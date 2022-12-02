;; # 🎄 Advent of Code: Day 2
^{:nextjournal.clerk/visibility {:code :hide}}
(ns advent-of-code.day-02
  (:require [nextjournal.clerk :as clerk]
            [advent-of-code.index :refer [badge]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

^{:nextjournal.clerk/visibility {:code :hide}}
(clerk/html (badge 2022))

(def input (->> "data/day02.txt"
                io/reader
                line-seq
                (map #(str/split % #" "))))


(defn outcome
  [[opp self]]
  (if (= opp self)
    3
    (case opp
      :rock (case self
              :paper 6
              :scissors 0)
      :paper (case self
               :rock 0
               :scissors 6)
      :scissors (case self
                  :rock 6
                  :paper 0))))

(defn match-value
  [[_ self :as match]]
  (+ (outcome match) ({:rock 1
                       :paper 2
                       :scissors 3} self)))
(def part-1
  (let [objects {"A" :rock
                 "B" :paper
                 "C" :scissors
                 "X" :rock
                 "Y" :paper
                 "Z" :scissors}]
    (->> input
         (map (fn [[opp self]]
                [(objects opp) (objects self)]))
         (map match-value)
         (apply +))))

(defn fix-match
  [[opp result]]
  [opp (case result
         :draw opp
         :win (case opp
                :rock :paper
                :paper :scissors
                :scissors :rock)
         :lose (case opp
                 :rock :scissors
                 :paper :rock
                 :scissors :paper))])


(def part-2
  (let [objects {"A" :rock
                 "B" :paper
                 "C" :scissors
                 "X" :lose
                 "Y" :draw
                 "Z" :win}]
    (->> input 
         (map (fn [[opp self]]
                [(objects opp) (objects self)]))
         (map fix-match)
         (map match-value)
         (apply +))))