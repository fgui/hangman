(ns hangman.db)

(def default-db
  {:name "re-frame"})

(def state {:language :en
            :accumulated-score 0
            :round {:word "word-to-guess" :tried-letters []}
            })
