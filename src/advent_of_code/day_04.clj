;; # 🎄 Advent of Code: Day 4
;; ![AoC Star Badge](https://raw.githubusercontent.com/casselc/aoc22/main/badges/badge.svg)
(ns advent-of-code.day-04
  (:require
   [clojure.set :refer [subset? intersection]]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [nextjournal.clerk :as clerk]))

(def input-lines (->> "data/day04.txt"
                      io/reader
                      line-seq))

(def set-pairs (->> input-lines
                    (map #(string/split % #","))
                    (map #(map (fn [s] (let [[a b] (map parse-long (string/split s #"-"))]
                                         (set (range a (inc b))))) %))))

(def part-1 (->> set-pairs
                 (filter (fn [[s1 s2]] (or (subset? s1 s2) (subset? s2 s1))))
                 count))

(def part-2 (->> set-pairs
                 (filter (fn [[s1 s2]] (seq (intersection s1 s2))))
                 count))

(defn overlaps? [[a b c d]]
  (and (<= a d) (<= c b)))
(overlaps? [1 3 2 4])