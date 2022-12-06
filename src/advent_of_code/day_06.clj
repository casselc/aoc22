;; # 🎄 Advent of Code: Day 6
(ns advent-of-code.day-06
  (:require
   [clojure.java.io :as io]
   [clojure.set :refer [intersection]]
   [clojure.string :as string]
   [nextjournal.clerk :as clerk]))

(def input (->> "data/day06.txt"
                io/reader
                line-seq
                first))

(def part-1 (->> input
                 (partition 4 1)
                 (map distinct)
                 (take-while #(< (count %) 4))
                 count
                 (+ 4)))


(def part-2 (->> input
                 (partition 14 1)
                 (map distinct)
                 (take-while #(< (count %) 14))
                 count
                 (+ 14)))