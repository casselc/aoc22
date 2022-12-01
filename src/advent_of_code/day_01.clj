;; # 🎄 Advent of Code: Day 1
(ns advent-of-code.day-01
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def input (->> "data/day01.txt"
                io/reader
                line-seq
                (map parse-long)))

(def part-1 (->> input
                 (partition-by nil?)
                 (take-nth 2)
                 (map #(apply + %))
                 sort
                 last))

(def part-2 (->> input
                 (partition-by nil?)
                 (take-nth 2)
                 (map #(apply + %))
                 sort
                 (take-last 3)
                 (apply +)))


