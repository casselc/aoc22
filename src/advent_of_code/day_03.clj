;; # 🎄 Advent of Code: Day 3
(ns advent-of-code.day-03
  (:require
   [clojure.set :refer [intersection]]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [nextjournal.clerk :as clerk]))

(def input-lines (->> "data/day03.txt"
                      io/reader
                      line-seq))

(def part-1 (->> input-lines
                 (map (fn [line]
                        (let [at (/ (.length line) 2)]
                          [(set (subs line 0 at)) (set (subs line at))])))
                 (map #(apply intersection %))
                 (map (comp #(if (>= % 97)
                               (- % 96)
                               (- % 38))
                            int
                            first))
                 (apply +)))

(def part-2 (->> input-lines
                 (partition 3)
                 (map (comp #(apply intersection %) #(map set %))) 
                 (map (comp #(if (>= % 97)
                               (- % 96)
                               (- % 38))
                            int
                            first))
                 (apply +)))