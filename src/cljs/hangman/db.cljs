(ns hangman.db
  (:require [hangman.game :as game]))

(def default-db
  {:name "re-frame"})

(def state {:accumulated-score 0
            :round {:word "word-to-guess" :tried-letters []}
            })
