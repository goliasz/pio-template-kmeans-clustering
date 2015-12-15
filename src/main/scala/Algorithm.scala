package org.template.clustering

import io.prediction.controller.P2LAlgorithm
import io.prediction.controller.Params
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.clustering.KMeansModel
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import grizzled.slf4j.Logger

case class AlgorithmParams(
  val numberOfCenters: Int,
  val numberOfIterations: Int
) extends Params

class Algorithm(val ap: AlgorithmParams)
  // extends PAlgorithm if Model contains RDD[]
  extends P2LAlgorithm[PreparedData, KMeansModel, Query, PredictedResult] {

  @transient lazy val logger = Logger[this.type]

  def train(data: PreparedData): KMeansModel = {
    
    println("Running the K-Means clustering algorithm.")
    val kMeans = new KMeans()
    kMeans.setK(ap.numberOfCenters)
	kMeans.setMaxIterations(ap.numberOfIterations)
    kMeans.run(data.points)
  }

  def predict(model: KMeansModel, query: Query): PredictedResult = {
    val result = model.predict(Vectors.dense(query.point))
    PredictedResult(cluster = result)
  }
}
