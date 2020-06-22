package com.guerra.enrico.navis_processor
//
//import com.google.auto.common.AnnotationMirrors
//import com.google.auto.common.BasicAnnotationProcessor
//import com.google.auto.common.MoreElements
//import com.google.auto.common.MoreTypes
//import com.google.auto.service.AutoService
//import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
//import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
//import com.guerra.enrico.navis_annotation.annotations.Input
//import com.guerra.enrico.navis_annotation.annotations.Portum
//import com.guerra.enrico.navis_annotation.annotations.Result
//import com.guerra.enrico.navis_annotation.annotations.Routes
//import com.guerra.enrico.navis_processor.models.ActivityRouteComponent
//import com.guerra.enrico.navis_processor.models.FragmentRouteComponent
//import com.guerra.enrico.navis_processor.models.PortumComponent
//import com.guerra.enrico.navis_processor.models.RoutesComponent
//import com.guerra.enrico.navis_processor.models.WithInputComponent
//import com.guerra.enrico.navis_processor.models.WithResultComponent
//import javax.annotation.processing.AbstractProcessor
//import javax.annotation.processing.Filer
//import javax.annotation.processing.Messager
//import javax.annotation.processing.ProcessingEnvironment
//import javax.annotation.processing.Processor
//import javax.annotation.processing.RoundEnvironment
//import javax.lang.model.SourceVersion
//import javax.lang.model.element.Element
//import javax.lang.model.element.ElementKind
//import javax.lang.model.element.TypeElement
//import javax.lang.model.type.TypeMirror
//import javax.tools.Diagnostic
//import kotlin.reflect.KClass
//
///**
// * Created by enrico
// * on 02/06/2020.
// */
////@AutoService(Processor::class) // Annotation to register the processor so it can be run in the build process
//class NavisProcessor : AbstractProcessor() {
//
//  private lateinit var messager: Messager
//  private lateinit var filer: Filer
//
//  private var portum: PortumComponent? = null
//  private var routes = mutableListOf<RoutesComponent>()
//
//  /**
//   * Method called only one time
//   */
//  @Synchronized
//  override fun init(processingEnv: ProcessingEnvironment) {
//    super.init(processingEnv)
//    messager = processingEnv.messager
//    filer = processingEnv.filer
//  }
//
//
//  override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
//    messager.printMessage(Diagnostic.Kind.NOTE, "Navis processor is running")
//
//    portum = getPortum(roundEnv)
//
//    routes = getRoutes(roundEnv)
//
//    roundEnv.getElementsAnnotatedWith(ActivityRoute::class.java).forEach { annotatedElement ->
//      val routeComponent = getActivityRoute(annotatedElement) ?: return true
//      routes
//        .firstOrNull { it.enclosingElement == routeComponent.enclosingElement }
//        ?.addActivityRoute(routeComponent)
//        ?: routes.add(
//          RoutesComponent(
//            enclosingElement = routeComponent.enclosingElement,
//            initialActivityRoutes = listOf(routeComponent)
//          )
//        )
//    }
//
//    roundEnv.getElementsAnnotatedWith(FragmentRoute::class.java).forEach { annotatedElement ->
//      val routeComponent = getFragmentRoute(annotatedElement) ?: return true
//      routes
//        .firstOrNull { it.enclosingElement == routeComponent.enclosingElement }
//        ?.addFragmentRoute(routeComponent)
//        ?: routes.add(
//          RoutesComponent(
//            enclosingElement = routeComponent.enclosingElement,
//            initialFragmentRoutes = listOf(routeComponent)
//          )
//        )
//    }
//
//    val portumComponent = portum ?: return true
//
//    messager.printMessage(Diagnostic.Kind.WARNING, "---- portum set \n")
//
//    portumComponent.addAllRoutes(routes)
//    val generator = Generator(messager, portumComponent)
//
//    try {
//      generator.build()
//        .forEach { it.writeTo(filer) }
//
//      // Important to clear resource for next rounds
//      routes.clear()
//    } catch (e: Exception) {
//      messager.printMessage(Diagnostic.Kind.ERROR, "---- error write: ${e}")
//    }
//
//    return true
//
//  }
//
//  override fun getSupportedAnnotationTypes(): Set<String> {
//    return setOf(
//      Portum::class.java.canonicalName,
//      Routes::class.java.canonicalName,
//      FragmentRoute::class.java.canonicalName,
//      ActivityRoute::class.java.canonicalName,
//      Input::class.java.canonicalName,
//      Result::class.java.canonicalName
//    )
//  }
//
//  override fun getSupportedSourceVersion(): SourceVersion {
//    return SourceVersion.latestSupported()
//  }
//
//  private fun getPortum(roundEnv: RoundEnvironment): PortumComponent? {
//    val element = roundEnv.getElementsAnnotatedWith(Portum::class.java)
//      .firstOrNull { annotatedElement ->
//        isAnnotatingInterface(annotatedElement, Portum::class.java.canonicalName)
//      } ?: return null
//    val enclosingClass = element as TypeElement
//    return PortumComponent(enclosingClass)
//  }
//
//  private fun getRoutes(roundEnv: RoundEnvironment): MutableList<RoutesComponent> {
//    val routes = mutableListOf<RoutesComponent>()
//    roundEnv.getElementsAnnotatedWith(Routes::class.java).forEach { annotatedElement ->
//      if (!isAnnotatingClass(annotatedElement, Routes::class.java.canonicalName)) {
//        return mutableListOf()
//      }
//      val enclosingClass = annotatedElement as TypeElement
//      val routesComponent = RoutesComponent(enclosingClass)
//      routes.add(routesComponent)
//    }
//    return routes
//  }
//
//  private fun getActivityRoute(element: Element): ActivityRouteComponent? {
//    if (!isAnnotatingClass(element, ActivityRoute::class.java.canonicalName)) {
//      return null
//    }
//
//    val enclosingElement = element.enclosingElement as TypeElement
//
//    val className = getInjectedClassName(element, ActivityRoute::class) ?: return null
//    val withInputComponent = getInputIfSet(element)
//    val withResultComponent = getResultIfSet(element)
//
//    return ActivityRouteComponent(
//      enclosingElement = enclosingElement,
//      elementName = element.simpleName.toString(),
//      className = className,
//      withInputComponent = withInputComponent,
//      withResultComponent = withResultComponent
//    )
//  }
//
//  private fun getFragmentRoute(element: Element): FragmentRouteComponent? {
//    if (!isAnnotatingClass(element, FragmentRoute::class.java.canonicalName)) {
//      return null
//    }
//
//    val enclosingElement = element.enclosingElement as TypeElement
//
//    val className = getInjectedClassName(element, FragmentRoute::class) ?: return null
//    val withInputComponent = getInputIfSet(element)
//
//    if (element.getAnnotation(Result::class.java) != null) {
//      val message =
//        "${Result::class.java.canonicalName} is not supported for ${FragmentRoute::class.java.canonicalName}, it will be ignored: ${element.simpleName}"
//      messager.printMessage(Diagnostic.Kind.WARNING, message, element)
//    }
//
//    return FragmentRouteComponent(
//      enclosingElement = enclosingElement,
//      elementName = element.simpleName.toString(),
//      className = className,
//      withInputComponent = withInputComponent
//    )
//  }
//
//  private fun getInputIfSet(element: Element): WithInputComponent? {
//    val className = getInjectedClassName(element, Input::class) ?: return null
//    return WithInputComponent(
//      key = "${element.simpleName}_param1",
//      className = className
//    )
//  }
//
//  private fun getResultIfSet(element: Element): WithResultComponent? {
//    val className = getInjectedClassName(element, Result::class) ?: return null
//
//    return WithResultComponent(
//      code = 1,
//      dataKey = "${element.simpleName}_result1",
//      className = className
//    )
//  }
//
//  private fun isAnnotatingClass(element: Element, annotationName: String): Boolean {
//    if (element.kind != ElementKind.CLASS) {
//      val message = "Only classes can be annotated with $annotationName"
//      messager.printMessage(Diagnostic.Kind.ERROR, message, element)
//      return false
//    }
//    return true
//  }
//
//  private fun isAnnotatingInterface(element: Element, annotationName: String): Boolean {
//    if (element.kind != ElementKind.INTERFACE) {
//      val message = "Only interfaces can be annotated with $annotationName"
//      messager.printMessage(Diagnostic.Kind.ERROR, message, element)
//      return false
//    }
//    return true
//  }
//
//  private fun getInjectedClassName(element: Element, annotation: KClass<out Annotation>): String? {
//    val annotationMirror = MoreElements.getAnnotationMirror(element, annotation.java)
//    if (!annotationMirror.isPresent) {
//      messager.printMessage(Diagnostic.Kind.WARNING, "not have $annotation", element)
//      return null
//    }
//    val annotationValue = AnnotationMirrors.getAnnotationValue(annotationMirror.get(), "value").value
//    return when (annotationValue) {
//      is TypeMirror -> {
//        val type = MoreTypes.asTypeElement(annotationValue)
//        type.qualifiedName.toString()
//      }
//      else -> null
//    }
//  }
//
//}