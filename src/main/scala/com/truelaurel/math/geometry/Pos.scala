package com.truelaurel.math.geometry

case class Pos(x: Int, y: Int) {
  def this(x: Double, y: Double) = this(x.toInt, y.toInt)

  def neighbours4: Seq[Pos] =
    Seq(Pos(x + 1, y), Pos(x - 1, y), Pos(x, y - 1), Pos(x, y + 1))

  def +(pos: Pos): Pos = Pos(x + pos.x, y + pos.y)

  def /(r: Int): Pos = Pos(x / r, y / r)

  def %(r: Int): Pos = Pos(x % r, y % r)

  def neighborIn(direction: Direction): Pos =
    direction match {
      case N  => Pos(x, y - 1)
      case S  => Pos(x, y + 1)
      case W  => Pos(x - 1, y)
      case E  => Pos(x + 1, y)
      case NE => Pos(x + 1, y - 1)
      case SE => Pos(x + 1, y + 1)
      case NW => Pos(x - 1, y - 1)
      case SW => Pos(x - 1, y + 1)
    }

  def distance(pos: Pos): Int =
    Math.max(Math.abs(x - pos.x), Math.abs(y - pos.y))

  def distanceEuclide(pos: Pos): Double =
    Math.sqrt(Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2))
}

object Pos {
  val right: (Int, Int) = (1, 0)
  val down: (Int, Int) = (0, 1)
  val downRight: (Int, Int) = (1, 1)
  val downLeft: (Int, Int) = (-1, 1)
  val all = Seq(right, down, downRight, downLeft)
}

sealed trait Direction {
  def similar: Array[Direction]
}

case object N extends Direction {
  val similar: Array[Direction] = Array(NE, N, NW)
}

case object W extends Direction {
  val similar: Array[Direction] = Array(NW, W, SW)
}

case object S extends Direction {
  val similar: Array[Direction] = Array(SE, S, SW)
}

case object E extends Direction {
  val similar: Array[Direction] = Array(NE, SE, E)
}

case object NW extends Direction {
  val similar: Array[Direction] = Array(N, W, NW)
}

case object NE extends Direction {
  val similar: Array[Direction] = Array(NE, N, E)
}

case object SW extends Direction {
  val similar: Array[Direction] = Array(S, W, SW)
}

case object SE extends Direction {
  val similar: Array[Direction] = Array(SE, E, S)
}

object Direction {

  /**
    * @param size of the square
    * @return the valid neighbors
    */
  def neighborsOf(pos: Pos, size: Int): Seq[Pos] = {
    Direction.all
      .map(d => pos.neighborIn(d))
      .filter(p => p.x < size && p.x >= 0 && p.y < size && p.y >= 0)
  }

  val all = Seq(N, W, S, E, SW, SE, NW, NE)

  def apply(dir: String): Direction =
    dir match {
      case "N"  => N
      case "S"  => S
      case "W"  => W
      case "E"  => E
      case "NE" => NE
      case "SE" => SE
      case "NW" => NW
      case "SW" => SW
      case _    => throw new IllegalArgumentException("unknown direction " + dir)
    }
}
