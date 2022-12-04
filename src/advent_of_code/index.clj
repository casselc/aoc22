;; # 🎄 Advent of Code 2022
(ns advent-of-code.index
  {:nextjournal.clerk/visibility {:code :hide :result :hide}}
  (:require [babashka.fs :as fs]
            [clj-http.client :as client]
            [clojure.string :as str]
            [nextjournal.clerk :as clerk]
            [hickory.core :as h]
            [hickory.select :as s]
            [hiccup2.core :refer [html]]))

(defn badge
  [year]
  (let [stars (or (when-let [token (System/getenv "AOC_SESSION_TOKEN")]
                    (->> (client/get (str "https://adventofcode.com/" year) {:headers {"Cookie" (str "session=" token)}})
                         :body
                         h/parse
                         h/as-hickory
                         (s/select (s/class "star-count"))
                         first
                         :content
                         first))
                  "0*")
        title (str year ": " stars)]
    [:svg {:xmlns "http://www.w3.org/2000/svg", :xmlns:xlink "http://www.w3.org/1999/xlink", :width "85" :height "20", :role "img", :aria-label title}
     [:title title]
     [:lineargradient {:id "s", :x2 "0", :y2 "100%"}
      [:stop {:offset "0", :stop-color "#aaa", :stop-opacity ".2"}]
      [:stop {:offset "1", :stop-color "#333", :stop-opacity ".2"}]]
     [:clipPath {:id "r"}
      [:rect {:width "85", :height "20", :rx "4", :fill "#fff"}]]
     [:g {:clip-path "url(#r)"}
      [:rect {:width "54", :height "20", :fill "#0a0e25"}]
      [:rect {:x "54", :width "31", :height "20", :fill "#00cc00"}]
      [:rect {:width "85", :height "20", :fill "url(#s)"}]]
     [:g {:fill "#fff", :text-anchor "middle", :font-family "Verdana,Geneva,DejaVu Sans,sans-serif", :text-rendering "geometricPrecision", :font-size "110"}
      [:g
       (for [i (range 5)
             :let [deg (* i 72)]]
         [:line {:x1 "12", :y1 "10", :x2 "12", :y2 "4", :stroke "yellow", :stroke-width "2" :transform (str "rotate(" deg ", 12, 10)")}])]
      [:text {:aria-hidden "true", :x "365", :y "140", :fill "#010101", :fill-opacity ".3", :transform "scale(.1) translate(4,4)", :textlength "270"} year]
      [:text {:x "365", :y "140", :transform "scale(.1)", :fill "#fff", :textlength "270"} year]
      [:text {:aria-hidden "true", :x "685", :y "140", :fill "#010101", :fill-opacity ".3", :transform "scale(.1) translate(4, 4)", :textlength "210"} stars]
      [:text {:x "685", :y "140", :transform "scale(.1)", :fill "#fff", :textlength "210"} stars]]]))

(defn build-paths
  "Computes the paths to build by looking for files in
  `src/advent_of_code` and filtering out unmodified templates (files
  with less than four lines)."
  []
  (into []
        (keep (fn [day]
                (let [f (fs/file "src" "advent_of_code" (format "day_%s.clj" (cond->> day (<= day 10) (str "0"))))]
                  (when (and (.exists f)
                             (< 3 (count (str/split-lines (slurp f)))))
                    (str f)))))
        (range 25)))

{:nextjournal.clerk/visibility {:result :show}}

^::clerk/no-cache
(let [curr-badge (badge 2022)]
  (spit "badges/badge.svg" (html curr-badge))
  (clerk/html
   [:article
    [:h2 "Solutions"]
    (into [:ul] (mapv (fn [path]
                        (when-let [day (second (re-matches #".*day_(\d+).clj" path))]
                          [:li [:a {:href (clerk/doc-url path)} "Day " day]])) (build-paths)))
    [:footer curr-badge]]))

