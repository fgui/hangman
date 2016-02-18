(ns hangman.dictionary)

(def words
  {:en
   ["clojure" "ruby" "python" "java"]
   :es
   ["hola" "mundo" "café"]
   })

(defn random-word [language] (rand-nth (words language)))
