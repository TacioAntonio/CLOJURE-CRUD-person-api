(ns app.utils.functions
  (:import [java.util UUID]))

(defn valid-email? [email]
  (let [email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"]
    (re-matches email-regex email)))

(defn generate-random-id []
  (str (.toString (UUID/randomUUID))))
