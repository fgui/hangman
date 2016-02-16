(ns hangman.dictionary)

(def words ["clojure" "ruby" "python" "java"])
(defn random-word [] (rand-nth words))
