(ns hangman.db)

(def default-db
  {:name "re-frame"})

(def hangman {:word"hola"
              :tried-letters []
              :num-errors 0
              :display-word "____"})
