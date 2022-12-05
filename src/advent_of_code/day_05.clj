;; # 🎄 Advent of Code: Day 5
;; ![AoC Star Badge](https://raw.githubusercontent.com/casselc/aoc22/main/badges/badge.svg)
(ns advent-of-code.day-05
  (:require 
   [clojure.java.io :as io]
   [clojure.string :as string]
   [nextjournal.clerk :as clerk]))

(def input-lines (->> "data/day05.txt"
                      io/reader
                      line-seq))

(def stacks (->> input-lines
                 (partition-by string/blank?)
                 first
                 (apply map vector)
                 (drop 1)
                 (take-nth 4)
                 (map #(vec (drop-while (fn [c] (or (nil? c) (= \  c))) (drop-last 1 %))))
                 vec))

(def moves (->> input-lines
                (partition-by string/blank?)
                (drop 2)
                first
                (map #(map parse-long (re-seq #"\d+" %)))))

(def part-1
  (loop [stacks stacks
         moves moves] 
    (if-let [[qty from to] (first moves)]
      (let [src (get stacks (dec from) [])
            dest (get stacks (dec to) [])
            to-move (take qty src)] 
        (recur (-> stacks
                   (assoc (dec to) (apply conj (vec (reverse to-move)) dest))
                   (assoc (dec from) (vec (drop qty src))))
               (rest moves)))
      (apply str (map first stacks)))))

(def part-2
  (loop [stacks stacks
         moves moves]
    (if-let [[qty from to] (first moves)]
      (let [src (get stacks (dec from) [])
            dest (get stacks (dec to) [])
            to-move (take qty src)] 
        (recur (-> stacks
                   (assoc (dec to) (apply conj (vec to-move) dest))
                   (assoc (dec from) (vec (drop qty src))))
               (rest moves)))
      (apply str (map first stacks)))))


