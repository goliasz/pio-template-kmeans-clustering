package org.template.clustering

import io.prediction.controller.P2LAlgorithm
import io.prediction.controller.Params
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import grizzled.slf4j.Logger

case class AlgorithmParams(
  val numberOfCenters: Int,
  val numberOfIterations: Int,
  val numberOfRuns: Int,
  val initMode: String,
  val seed: Int,
  val saveClusterCenters: Boolean
) extends Params

class KMeansAlgorithm(val ap: AlgorithmParams) extends P2LAlgorithm[PreparedData, KMeansModel, Query, PredictedResult] {

  @transient lazy val logger = Logger[this.type]

  def train(sc: SparkContext, data: PreparedData): KMeansModel = {
    println("Running the K-Means clustering algorithm.")
    val model = KMeans.train(data.points.cache, ap.numberOfCenters, ap.numberOfIterations, ap.numberOfRuns, ap.initMode, ap.seed)

    //We can save cluster centers to parquet file.
    if (ap.saveClusterCenters) {
      val sqlContext = new SQLContext(sc)
      import sqlContext.implicits._

      val centersRdd = sc.parallelize(model.clusterCenters.toList.map(_.toArray)).zipWithIndex
      centersRdd.toDF("center", "index").write.json("/tmp/KMeansModel-centers-"+System.currentTimeMillis)
    }

    model
  }

  def predict(model: KMeansModel, query: Query): PredictedResult = {
    val result = model.predict(Vectors.dense(query.point))
    PredictedResult(cluster = result)
  }
}
