package org.template.clustering

import org.apache.predictionio.controller.{Engine,EngineFactory}

case class Query(
  val point: Array[Double]
) extends Serializable

case class PredictedResult(
  val cluster: Double,
  val clusterCenter: Array[Double]
) 
extends Serializable

case class ActualResult(
  val cluster: Double,
  val clusterCenter: Array[Double]
) 
extends Serializable

object ClusteringEngine extends EngineFactory {
  def apply() = {
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("kmeans" -> classOf[KMeansAlgorithm]),
      	classOf[Serving])
  }
}
