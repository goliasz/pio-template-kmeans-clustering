package org.template.clustering

import io.prediction.controller.PDataSource
import io.prediction.controller.EmptyEvaluationInfo
import io.prediction.controller.EmptyActualResult
import io.prediction.controller.Params
import io.prediction.data.storage.Event
import io.prediction.data.storage.Storage
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

import grizzled.slf4j.Logger

case class DataSourceParams(appName: String) extends Params

class DataSource(val dsp: DataSourceParams)
  extends PDataSource[TrainingData,
    EmptyEvaluationInfo, Query, EmptyActualResult] {

  @transient lazy val logger = Logger[this.type]

  override
  def readTraining(sc: SparkContext): TrainingData = {
    val points = Storage.getPEvents()

    println("Gathering data from event server.")
	val pointsRDD: RDD[Vector] = points.aggregateProperties(
      appName = dsp.appName,
      entityType = "point",
      required = Some(List("attr0","attr1")))(sc)
	  .map { case (entityId, properties) =>
        try {
			Vectors.dense(Array(
              properties.get[Double]("attr0"),
              properties.get[Double]("attr1")
        ))
          
        } catch {
          case e: Exception => {
            logger.error(s"Failed to get properties ${properties} of" +
              s" ${entityId}. Exception: ${e}.")
            throw e
          }
        }
      }
		
    new TrainingData(pointsRDD)
  }
}

class TrainingData(
  val points: RDD[Vector]
) extends Serializable {
  override def toString = {
    s"events: [${points.count()}] (${points.take(2).toList}...)"
  }
}
