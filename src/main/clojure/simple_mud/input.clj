(ns simple-mud.input
  (:require [the.parsatron :as p
             :refer [defparser run always attempt choice token many many1 digit
                     eof let->>]]))


(def whitespace-char (token #{\space \newline \tab}))
(def optional-whitespace (many whitespace-char))
(def whitespace (many1 whitespace-char))


(defparser number []
  (let->> [digits (many1 (digit))]
    (always (Long/parseLong (apply str digits)))))

(defparser basic-command [full abbrev result]
  (choice (attempt (p/string full))
          (attempt (p/string abbrev)))
  (always result))

(defparser north [] (basic-command "north" "n" :north))
(defparser south [] (basic-command "south" "s" :south))
(defparser east []  (basic-command "east"  "e" :east))
(defparser west []  (basic-command "west"  "w" :west))

(defparser direction []
  (choice (north) (south) (east) (west)))

(defparser look []
  (basic-command "look" "l" :look))

(defparser inventory []
  (basic-command "inventory" "i" :inventory))

(defparser parser []
  (let->> [result (choice (attempt (direction))
                          (attempt (inventory))
                          (attempt (look))
                          (attempt (number)))
           _ (eof)]
    (always result)))

(defn parse [s]
  (run (parser) s))

(comment
  (parse "100")
  (parse "n")
  (parse "l")
  (parse "look")
  (parse "i")
  )
