(ns user
  (:require [nextjournal.clerk :as clerk]
            [advent-of-code.index :as index]))

(clerk/serve! {:port 7878 :browse true})

(comment
  (clerk/build! {:paths-fn index/build-paths :browse true}))

