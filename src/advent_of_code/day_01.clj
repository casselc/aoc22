;; # 🎄 Advent of Code: Day 1
^{:nextjournal.clerk/visibility {:code :hide}}
(ns advent-of-code.day-01
  (:require [nextjournal.clerk :as clerk]
            [advent-of-code.index :refer [badge]]
            [clojure.java.io :as io]))

^{:nextjournal.clerk/visibility {:code :hide}}
(clerk/html (badge 2022))

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