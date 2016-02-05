(ns hangman.db
  (:require [hangman.game :as game]))

(def default-db
  {:name "re-frame"})

(def state {:word (game/guess-this-word "hola")
              :playerA {}
              :playerB {}
              :round :playerA
              :mode :request ;; :play
              })
