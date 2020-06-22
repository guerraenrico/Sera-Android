package com.guerra.enrico.navis_processor

import com.google.auto.common.MoreElements
import com.guerra.enrico.navis_annotation.contracts.WithInput
import com.guerra.enrico.navis_annotation.contracts.WithResult
import com.guerra.enrico.navis_processor.models.ActivityRouteComponent
import com.guerra.enrico.navis_processor.models.PortumComponent
import com.guerra.enrico.navis_processor.models.RouteComponent
import com.guerra.enrico.navis_processor.models.RoutesComponent
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Suppress("UnstableApiUsage")
internal class FileGenerator(
  private val portumComponent: PortumComponent
) {

  fun build(): List<FileSpec> {
    return portumComponent.routes.map {
      generateCode(it)
    }
  }

  private fun generateCode(routesComponent: RoutesComponent): FileSpec {
    val className: String = getClassName(routesComponent.enclosingElement)
    val routes = TypeSpec.objectBuilder(className)

    routesComponent.activityRoutes.forEach { routeComponent ->
      val route = generateRoutes(routeComponent)
      routes.addType(route)
    }

    routesComponent.fragmentRoutes.forEach { routeComponent ->
      val route = generateRoutes(routeComponent)
      routes.addType(route)
    }

    val file = FileSpec.builder(packageName, className)
      .addType(routes.build())
      .build()

    return file
  }

  private fun generateRoutes(route: RouteComponent): TypeSpec {
    val routeObjectClass = TypeSpec.objectBuilder(route.elementName)

    val propsList = mutableListOf<PropertySpec>()

    route.withInputComponent?.let {
      val inputKeyProp = PropertySpec.builder("inputKey", String::class, KModifier.CONST)
        .initializer("%S", it.key)
        .build()
      propsList.add(inputKeyProp)
    }

    if (route is ActivityRouteComponent) {
      route.withResultComponent?.let {
        val resultCodeProp = PropertySpec.builder("resultCode", Int::class, KModifier.CONST)
          .initializer("${it.code}")
          .build()
        val resultKeyProp = PropertySpec.builder("resultKey", String::class, KModifier.CONST)
          .initializer("%S", it.dataKey)
          .build()
        propsList.addAll(listOf(resultCodeProp, resultKeyProp))
      }
    }

    routeObjectClass.addProperties(propsList)

    val buildTargetFunction = generateBuildTargetFunction(route)
    routeObjectClass.addFunction(buildTargetFunction)

    return routeObjectClass.build()
  }

  private fun generateBuildTargetFunction(route: RouteComponent): FunSpec {
    val buildTargetFunction = FunSpec.builder("buildTarget")
      .returns(route.targetKClass)

    route.withInputComponent?.let {
      buildTargetFunction.addParameter("input", it.className.toKClass())
    }

    val obj = generateBuildTargetReturnObject(route)
    buildTargetFunction.addStatement("return %L", obj)

    return buildTargetFunction.build()
  }

  private fun generateBuildTargetReturnObject(route: RouteComponent): TypeSpec {
    val objectClass = TypeSpec.anonymousClassBuilder()
      .addSuperinterface(route.targetKClass)

    objectClass.addProperty(generateClassNameProp(route.className))

    route.withInputComponent?.let {
      val kClass = it.className.toKClass()
      objectClass.addSuperinterface(WithInput::class.parameterizedBy(kClass))
      objectClass.addProperties(generateInputsParameter(it.key, kClass))
    }

    if (route is ActivityRouteComponent) {
      route.withResultComponent?.let {
        val kClass = it.className.toKClass()
        objectClass.addSuperinterface(WithResult::class.parameterizedBy(kClass))
        objectClass.addProperties(generateResultParameter())
      }
    }

    return objectClass.build()
  }

  // TODO Try to generalize
  private fun generateClassNameProp(className: String): PropertySpec {
    return generateStringProp(
      name = "className",
      value = className,
      modifier = KModifier.OVERRIDE
    )
  }

  // TODO Try to generalize
  private fun generateInputsParameter(key: String, paramsType: KClass<*>): List<PropertySpec> {
    val keyProp = generateStringProp("key", key, KModifier.OVERRIDE)
    val paramsProp = PropertySpec.builder("params", paramsType, KModifier.OVERRIDE)
      .initializer("%L", "input")
      .build()
    return listOf(keyProp, paramsProp)
  }

  // TODO Try to generalize
  private fun generateResultParameter(): List<PropertySpec> {
    val codeProp = PropertySpec.builder("code", Int::class, KModifier.OVERRIDE)
      .initializer("%L", "resultCode")
      .build()
    val dataKeyPro = PropertySpec.builder("dataKey", String::class, KModifier.OVERRIDE)
      .initializer("%L", "resultKey")
      .build()
    return listOf(codeProp, dataKeyPro)
  }

  private fun generateStringProp(name: String, value: String, modifier: KModifier): PropertySpec {
    return PropertySpec.builder(name, String::class, modifier)
      .initializer("%S", value)
      .build()
  }

  private fun getClassName(enclosingElement: TypeElement): String {
    return enclosingElement.qualifiedName
      .toString()
      .substring(getPackageName(enclosingElement).length + 1)
      .replace(".", "")
      .replace("(.)(\\p{Upper})".toRegex(), "$1_$2")
      .plus("Routes")
  }

  private val packageName: String = getPackageName(portumComponent.enclosingClass)

  private fun getPackageName(enclosingElement: TypeElement): String {
    return MoreElements.getPackage(enclosingElement).qualifiedName.toString()
  }
}