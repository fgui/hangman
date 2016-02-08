(ns hangman.game)

(def config {:max-errors 10})

(defn guess-this-word [word]
  {:word-to-guess word
   :tried-letters []})

(defn includes? [coll elem]
  ((set coll) elem))

(defn misses [game-word]
  (filter
   #(not (includes? (:word-to-guess game-word) %))
   (:tried-letters game-word)))

(defn num-errors [game-word]
  (count (misses game-word)))

(defn lost? [game-word]
  (= (:max-errors config) (num-errors game-word)))

(defn won? [game-word]
  (let [letters-in-word-to-guess (set (:word-to-guess game-word))
        tried-letters (set (:tried-letters game-word))]
    (clojure.set/subset? letters-in-word-to-guess
                         tried-letters)))

(defn game-over? [game-word]
  (or (lost? game-word) (won? game-word)))

(defn word-so-far
  [game-word]
  (map
   #(if (includes? (:tried-letters game-word) %) % :no-letter)
   (:word-to-guess game-word)))

(defn display-word [game-word]
  (clojure.string/join " "
                       (map #(if (= :no-letter %) "_" %)
                            (word-so-far game-word))))

(defn display-misses [game-word]
  (clojure.string/join ", " (misses game-word)))

(defn display-result [game-word]
  (if (won? game-word) "won" "lost"))

;; I would like to give points for each word.
;; You start with 100 points and each fail we subtract 10
