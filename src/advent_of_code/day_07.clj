;; # 🎄 Advent of Code: Day 7
(ns advent-of-code.day-07
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]
   [nextjournal.clerk :as clerk]))

(def input (->> "data/day07.txt"
                io/reader
                line-seq))

(defn inc-size [dirs cwd size] 
  (loop [d dirs
         p cwd]
    (if-not (empty? p)
      (recur (update d p #(+ % size)) (pop p))
      d)))

(def dir-sizes
  (first (reduce (fn [[dirs cwd] line]
                   (let [[_ cmd cd-dir] (re-matches #"^\$ (\w+)\s?([\w\./]*)" line)
                         [_ dir] (re-matches #"^dir (\w+)" line)
                         [_ size file] (re-matches #"^(\d+) ([\w\.]+)" line)]
                     (case cmd
                       "cd" (if (= cd-dir "..")
                              [dirs (pop cwd)]
                              [dirs (conj cwd cd-dir)])
                       "ls" [dirs cwd]
                       (if dir
                         [(assoc dirs (conj cwd dir) 0) cwd]
                         [(inc-size dirs cwd (parse-long size)) cwd]))))
                 [{["/"] 0} []]
                 input)))


(def part-1 (->> dir-sizes
                 vals
                 (filter #(<= % 100000))
                 (apply +)))

(def part-2-target (- (dir-sizes
                       ["/"]) 40000000))

(def part-2 (->> dir-sizes
                 vals
                 sort
                 (drop-while #(< % part-2-target))
                 first))