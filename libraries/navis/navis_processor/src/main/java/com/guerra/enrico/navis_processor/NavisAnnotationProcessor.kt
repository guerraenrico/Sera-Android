package com.guerra.enrico.navis_processor

import com.google.auto.common.BasicAnnotationProcessor
import com.google.auto.service.AutoService
import com.guerra.enrico.navis_processor.models.GeneralComponent
import com.guerra.enrico.navis_processor.step.route.RoutesProcessingStep
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.tools.Diagnostic

/**
 * Created by enrico
 * on 04/06/2020.
 *
 * TODO: implement:
 * - create extension functions for fragment and activities to get input
 *
 */

@AutoService(Processor::class)
internal class NavisAnnotationProcessor : BasicAnnotationProcessor() {

  class Graph {
    var generalComponent: GeneralComponent? = null
  }

  private val graph = Graph()
  private val optionParser = OptionParser()

  override fun initSteps(): MutableIterable<ProcessingStep> {
    val messager = processingEnv.messager
    return mutableListOf(RoutesProcessingStep(graph, messager))
  }

  override fun getSupportedOptions(): MutableSet<String> {
    return optionParser.supportedOptions.toMutableSet()
  }

  override fun postRound(roundEnv: RoundEnvironment) {
    val messager = processingEnv.messager

    val portumComponent = graph.generalComponent ?: return

    if (roundEnv.processingOver()) {

      val filePath = optionParser.filePath(processingEnv.options)
      val generator = FileGenerator(portumComponent)

      try {
        generator.build().forEach { it.writeTo(File(filePath)) }

        graph.generalComponent = null
      } catch (e: Exception) {
        messager.printMessage(Diagnostic.Kind.ERROR, "---- error write: $e")
      }
    }
  }

}