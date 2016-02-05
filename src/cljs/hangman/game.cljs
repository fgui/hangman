(ns hangman.game)

(def config {:max-errors 10})

(defn guess-this-word [word]
  {:word-to-guess word
   :tried-letters []})

(defn tried-letters-set [game-word]
  (set (:tried-letters game-word)))

(defn word-to-guess-set [game-word]
  (set (:word-to-guess game-word)))

(defn misses [game-word]
  (filter
   #(not ((word-to-guess-set game-word) %))
   (:tried-letters game-word)))

(defn num-errors [game-word]
  (count (misses game-word)))

(defn lost? [game-word]
  (= (:max-errors config) (num-errors game-word)))

(defn won? [game-word]
  (clojure.set/subset? (word-to-guess-set game-word)
                       (tried-letters-set game-word)))

(defn game-over? [game-word]
  (or (lost? game-word) (won? game-word)))

(defn display-word [game-word]
  (map
   #(if ((tried-letters-set game-word) %) % "_")
   (:word-to-guess game-word)))

(defn display-misses [game-word]
  (clojure.string/join ", " (misses game-word)))

(defn display-result [game-word]
  (if (won? game-word) "won" "lost"))

;; I would like to give points for each word.
;; You start with 100 points and each fail we subtract 10
