package com.guerra.enrico.navis_processor

import com.google.auto.common.BasicAnnotationProcessor
import com.google.auto.service.AutoService
import com.guerra.enrico.navis_processor.models.PortumComponent
import com.guerra.enrico.navis_processor.models.PortumComponent2
import com.guerra.enrico.navis_processor.step.portum.PortumProcessingStep
import com.guerra.enrico.navis_processor.step.route.RoutesProcessingStep
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.OriginatingElementsHolder
import java.io.File
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.tools.Diagnostic
import javax.tools.JavaFileManager
import javax.tools.StandardLocation

/**
 * Created by enrico
 * on 04/06/2020.
 */



@AutoService(Processor::class)
internal class NavisAnnotationProcessor : BasicAnnotationProcessor() {

  class Graph {
    var portumComponent = PortumComponent2()
  }

  private val graph = Graph()

  override fun initSteps(): MutableIterable<ProcessingStep> {
    val messager = processingEnv.messager
    messager.printMessage(Diagnostic.Kind.WARNING, "----initstep")

    return mutableListOf(
//      PortumProcessingStep(graph, messager),
      RoutesProcessingStep(graph, messager)
    )
  }

  override fun postRound(roundEnv: RoundEnvironment) {
    val portumComponent = graph.portumComponent
    val messager = processingEnv.messager

    messager.printMessage(Diagnostic.Kind.WARNING, "----postRound: ${portumComponent}")

    if (roundEnv.processingOver()) {

      val generator = Generator(messager, portumComponent)

      try {
        generator.build()
//          .forEach { it.writeTo(processingEnv.filer) }
          // TODO WTF
          .forEach { it.writeTo(File("D:\\Progetti\\Sera\\Android\\Sera\\navigation\\build\\generated\\source\\kapt\\debug")) }

        // Important to clear resource for next rounds
        portumComponent.clearRoutes()
      } catch (e: Exception) {
        messager.printMessage(Diagnostic.Kind.ERROR, "---- error write: ${e}")
      }
    }
  }


}