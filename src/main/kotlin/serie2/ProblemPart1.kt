package serie2

import java.io.File

class Point(
    val x: Double,
    val y: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point) return false
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String = "$x $y"
}

class PointsProcessor {
    private val pointsFile1 = mutableSetOf<Point>()
    private val pointsFile2 = mutableSetOf<Point>()
    private var loaded = false

    fun loadFiles(
        file1Path: String,
        file2Path: String,
    ) {
        pointsFile1.clear()
        pointsFile2.clear()

        loadPointsFromFile(file1Path, pointsFile1)
        loadPointsFromFile(file2Path, pointsFile2)

        loaded = true
        println("Files loaded successfully.")
    }

    private fun loadPointsFromFile(
        filePath: String,
        pointsSet: MutableSet<Point>,
    ) {
        File(filePath).forEachLine { line ->
            if (line.startsWith('v')) {
                val parts = line.split("\\s+".toRegex())
                if (parts.size >= 4) {
                    val x = parts[2].toDouble()
                    val y = parts[3].toDouble()
                    pointsSet.add(Point(x, y))
                }
            }
        }
    }

    fun union(outputPath: String) {
        checkLoaded()
        val unionSet = pointsFile1.union(pointsFile2)
        writePointsToFile(outputPath, unionSet)
        println("Union operation completed. Results saved to $outputPath")
    }

    fun intersection(outputPath: String) {
        checkLoaded()
        val intersectionSet = pointsFile1.intersect(pointsFile2)
        writePointsToFile(outputPath, intersectionSet)
        println("Intersection operation completed. Results saved to $outputPath")
    }

    fun difference(outputPath: String) {
        checkLoaded()
        val differenceSet = pointsFile1.subtract(pointsFile2)
        writePointsToFile(outputPath, differenceSet)
        println("Difference operation completed. Results saved to $outputPath")
    }

    private fun writePointsToFile(
        outputPath: String,
        points: Set<Point>,
    ) {
        File(outputPath).bufferedWriter().use { writer ->
            points.forEach { point ->
                writer.write("${point.x} ${point.y}")
                writer.newLine()
            }
        }
    }

    private fun checkLoaded() {
        if (!loaded) {
            throw IllegalStateException("Files must be loaded first using 'load' command")
        }
    }
}

fun main() {
    val processor = PointsProcessor()
    val scanner = java.util.Scanner(System.`in`)

    println("ProcessPointsCollections application")
    println("Available commands:")
    println("load <file1.co> <file2.co> - Load two point files")
    println("union <output.co> - Perform union operation")
    println("intersection <output.co> - Perform intersection operation")
    println("difference <output.co> - Perform difference operation")
    println("exit - Exit the application")

    while (true) {
        print("> ")
        val input = scanner.nextLine().trim()
        if (input.isEmpty()) continue

        val parts = input.split("\\s+".toRegex())
        val command = parts[0]

        try {
            when (command) {
                "load" -> {
                    if (parts.size != 3) {
                        println("Usage: load <file1.co> <file2.co>")
                    } else {
                        processor.loadFiles(parts[1], parts[2])
                    }
                }
                "union" -> {
                    if (parts.size != 2) {
                        println("Usage: union <output.co>")
                    } else {
                        processor.union(parts[1])
                    }
                }
                "intersection" -> {
                    if (parts.size != 2) {
                        println("Usage: intersection <output.co>")
                    } else {
                        processor.intersection(parts[1])
                    }
                }
                "difference" -> {
                    if (parts.size != 2) {
                        println("Usage: difference <output.co>")
                    } else {
                        processor.difference(parts[1])
                    }
                }
                "exit" -> {
                    println("Exiting application...")
                    return
                }
                else -> println("Unknown command: $command")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
