package org.template.clustering

import org.apache.predictionio.controller.PPreparator
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

class PreparedData(
  val points: RDD[Vector]
) extends Serializable

class Preparator
  extends PPreparator[TrainingData, PreparedData] {

  def prepare(sc: SparkContext, trainingData: TrainingData): PreparedData = {
    new PreparedData(points = trainingData.points)
  }
}


