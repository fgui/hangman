(ns hangman.round)

(def config
  {:max-errors 10
   :miss-score -5
   :hit-score 10
   :min-score 0})

(defn guess-this-word [word]
  {:word-to-guess word
   :tried-letters []})

(defn includes? [coll elem]
  ((set coll) elem))

(defn misses [round]
  (filter
   #(not (includes? (:word-to-guess round) %))
   (:tried-letters round)))

(defn num-errors [round]
  (count (misses round)))

(defn lost? [round]
  (= (config :max-errors) (num-errors round)))

(defn won? [round]
  (let [letters-in-word-to-guess (set (:word-to-guess round))
        tried-letters (set (:tried-letters round))]
    (clojure.set/subset? letters-in-word-to-guess
                         tried-letters)))

(defn over? [round]
  (or (lost? round) (won? round)))

(defn word-so-far
  [round]
  (map
   #(if (includes? (:tried-letters round) %) % :no-letter)
   (:word-to-guess round)))

(defn score-misses [round]
  (* (config :miss-score) (count (misses round))))

(defn score-hits [round]
  (* (config :hit-score) (count (remove #(= % :no-letter) (word-so-far round)))))

(defn score [round]
  (max (config :min-score) (+ (score-misses round) (score-hits round))))

(defn accumulated-score [state]
  (+ (:accumulated-score state) (score (:round state))))


;; I would like to give points for each word.
;; You start with 100 points and each fail we subtract 10
