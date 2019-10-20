package com.satelite

import java.io.File
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

internal const val MIN_CONFIDENCE_LEVEL = 80
internal const val DIR_PATH = "C:\\Users\\dsanchez\\Downloads\\sat_elite\\"
internal const val FIRE_POINT_RADIOUS = 3.0F
internal const val REGION_POINT_RADIOUS = 1.0F
internal const val EVERGLADES_LAT = 25.6643619F
internal const val EVERGLADES_LONG = -81.3646979F
internal const val AMOUNT_OF_ATTRIBUTES = 15

fun isThereFireInThisRegion(directoryPath: String, regionLat: Float, regionLong: Float): Boolean {
    val files = getFiles(directoryPath)

    if (files.size <= 1) {
        println("There are no files in this directory $directoryPath")
        return false
    }

    var isThereActiveFires = false

    files.subList(1, files.size).forEach { file ->
        isThereActiveFires = isThereAnyActiveFireInFileList(file, regionLat, regionLong)
        if (isThereActiveFires) return@forEach
    }

    if (isThereActiveFires) {
        println("There is a fire, send the alert for all users that live in the Everglades!!!")
    } else {
        println("No fires :)")
    }

    return false
}

fun main(args: Array<String>) {
    // To make java work
        println("Hello Nikko")
    }

internal fun test() {
    isThereFireInThisRegion(DIR_PATH, EVERGLADES_LAT, EVERGLADES_LONG)
}

internal fun getFiles(dirPath: String): List<File> {
    val fileList = ArrayList<File>()
    File(dirPath).walk().forEach { file -> fileList.add(file) }
    return fileList
}

internal fun isThereAnyActiveFireInFileList(file: File, regionLat: Float, regionLong: Float): Boolean {
    val fires = getFirePointsFromFile(file)
    println("There are ${fires.size} fires detected with confidence level $MIN_CONFIDENCE_LEVEL")
    return isThereAnActiveFiresWithinEverglades(fires, regionLat, regionLong)
}

internal fun isPointWithinLimits(xMin: Double, yMin: Double, xMax: Double, yMax: Double, x: Double, y: Double)
        = x > xMin && x < xMax && y > yMin && y < yMax

internal fun isThereAnActiveFiresWithinEverglades(firePoints: ArrayList<FirePoint>, regionLat: Float, regionLong: Float): Boolean {
    val regionXMax = regionLat + REGION_POINT_RADIOUS
    val regionYMax = regionLong + REGION_POINT_RADIOUS
    val regionXMin = regionLat - REGION_POINT_RADIOUS
    val regionYMin = regionLong - REGION_POINT_RADIOUS

    for (firePoint in firePoints) {
        val xMax = (firePoint.lat + FIRE_POINT_RADIOUS)
        val xMin = (firePoint.lat - FIRE_POINT_RADIOUS)
        val yMax = (firePoint.long + FIRE_POINT_RADIOUS)
        val yMin = (firePoint.long - FIRE_POINT_RADIOUS)

        for (x in regionXMin.toInt()..regionXMax.toInt()) {
            for (y in regionYMin.toInt()..regionYMax.toInt()) {
                val isPointWithinLimits = isPointWithinLimits(xMin, yMin, xMax, yMax, x.toDouble(), y.toDouble())
                if (isPointWithinLimits) {
                    println("$isPointWithinLimits. For everglade point = ($x,$y). fire max point = ($xMax,$yMax). fire min point = ($xMin, $yMin)")
                    println("Active fire matched. Confidence Level = ${firePoint.confidence}")
                    return isPointWithinLimits
                }
            }
        }
    }
    return false
}

internal fun getFirePointsFromFile(file: File): ArrayList<FirePoint> {
    val firePoints = ArrayList<FirePoint>()

    file.forEachLine {
        if (!it.contains("latitude")) {
            val items = it.split(",")
            if (items.isNotEmpty() && items.size == AMOUNT_OF_ATTRIBUTES) {
                val acquiredLong = convertStringParamsInInstantTime(acquiredDate = items[5], acquiredTime = items[6])
                if (acquiredLong != null && items[9].toInt() > MIN_CONFIDENCE_LEVEL) {
                    val firePoint = FirePoint(
                        lat = items[0].toDouble(),
                        long = items[1].toDouble(),
                        confidence = items[9].toInt(),
                        acquiredDateTime = acquiredLong
                    )
                    firePoints.add(firePoint)
                }
            }
        }
    }
    return firePoints
}

internal fun convertStringParamsInInstantTime(acquiredDate: String, acquiredTime: String): Long? {
    if (acquiredDate.isBlank() || acquiredTime.isBlank()) return null
    val acquiredDateWithDateFormat = SimpleDateFormat("yyyy-MM-dd").parse(acquiredDate)
    val hours = if (acquiredTime.length == 4) acquiredTime.substring(0, 2) else "${ acquiredTime[0] } "
    val minutes = acquiredTime.substring(acquiredTime.length - 2, acquiredTime.length)
    val acquiredTimeWithLongFormat = SimpleDateFormat("HHmm").parse("$hours$minutes")
    return acquiredDateWithDateFormat.time + acquiredTimeWithLongFormat.time
}
