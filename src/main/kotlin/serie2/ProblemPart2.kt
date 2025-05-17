package serie2.part2

import serie2.part4.HashMap
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

class ProcessPointsCollections {
    private var file1Points = HashMap<Point, Unit>()
    private var file2Points = HashMap<Point, Unit>()

    fun load(
        file1Path: String,
        file2Path: String,
    ) {
        // Substitui os clear() por novas instâncias
        file1Points = HashMap<Point, Unit>()
        file2Points = HashMap<Point, Unit>()

        loadFile(file1Path, file1Points)
        loadFile(file2Path, file2Points)
    }

    private fun loadFile(
        filePath: String,
        pointsMap: HashMap<Point, Unit>,
    ) {
        File(filePath).forEachLine { line ->
            if (line.startsWith("v ")) {
                val parts = line.split(" ")
                if (parts.size >= 4) {
                    val x = parts[2].toDouble()
                    val y = parts[3].toDouble()
                    pointsMap.put(Point(x, y), Unit)
                }
            }
        }
    }

    fun union(outputPath: String) {
        val unionSet = HashMap<Point, Unit>()

        for (point in file1Points) {
            unionSet.put(point.key, Unit)
        }
        for (point in file2Points) {
            unionSet.put(point.key, Unit)
        }

        writePointsToFile(outputPath, unionSet)
    }

    fun intersection(outputPath: String) {
        val intersectionSet = HashMap<Point, Unit>()
        for (point in file1Points) {
            if (file2Points.get(point.key) != null) {
                intersectionSet.put(point.key, Unit)
            }
        }
        writePointsToFile(outputPath, intersectionSet)
    }

    fun difference(outputPath: String) {
        val differenceSet = HashMap<Point, Unit>()
        for (point in file1Points) {
            if (file2Points.get(point.key) == null) {
                differenceSet.put(point.key, Unit)
            }
        }
        writePointsToFile(outputPath, differenceSet)
    }

    private fun writePointsToFile(
        outputPath: String,
        points: HashMap<Point, Unit>,
    ) {
        File(outputPath).bufferedWriter().use { writer ->
            for (point in points) {
                writer.write("${point.key.x} ${point.key.y}\n")
            }
        }
    }
}

fun main() {
    val processor = ProcessPointsCollections()
    val scanner = java.util.Scanner(System.`in`)

    while (true) {
        print("> ")
        val input = scanner.nextLine().trim()
        if (input == "exit") break

        val parts = input.split(" ")
        when (parts[0]) {
            "load" -> {
                if (parts.size == 3) {
                    processor.load(parts[1], parts[2])
                    println("Files loaded successfully.")
                } else {
                    println("Usage: load <file1.co> <file2.co>")
                }
            }
            "union" -> {
                if (parts.size == 2) {
                    processor.union(parts[1])
                    println("Union operation completed. Output saved to ${parts[1]}")
                } else {
                    println("Usage: union <output.co>")
                }
            }
            "intersection" -> {
                if (parts.size == 2) {
                    processor.intersection(parts[1])
                    println("Intersection operation completed. Output saved to ${parts[1]}")
                } else {
                    println("Usage: intersection <output.co>")
                }
            }
            "difference" -> {
                if (parts.size == 2) {
                    processor.difference(parts[1])
                    println("Difference operation completed. Output saved to ${parts[1]}")
                } else {
                    println("Usage: difference <output.co>")
                }
            }
            else -> println("Unknown command. Available commands: load, union, intersection, difference, exit")
        }
    }
}
