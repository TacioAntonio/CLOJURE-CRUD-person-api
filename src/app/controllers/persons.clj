(ns app.controllers.persons
    (:require [clojure.data.json :as json]
              [cheshire.core :refer [parse-string]]
              [app.utils.fakeData :as fakeData]
              [app.utils.functions :as functions]))

(defn validate-person-fields [person]
  (cond
    (empty? (:Avatar person)) "Avatar is a required field"
    (empty? (:Firstname person)) "Firstname is a required field"
    (empty? (:Lastname person)) "Lastname is a required field"
    (not (functions/valid-email? (:Email person))) "Email is invalid"
    (or (<= (:Age person) 0) (> (:Age person) 120)) "Age is invalid"
    :else nil))

(defn find-index-by-id [collection id]
  (let [ids (map :Id collection)]
    (if (some #(= % id) ids)
      (first (keep-indexed (fn [idx val] (when (= val id) idx)) ids))
      nil)))

(defn get-all [request]
  {:status 200 :headers {"Content-Type" "application/json"} :body fakeData/PERSONS})

(defn get-by-id [request]
 (let [currentId (get-in request [:path-params :id])]
  (let [currentPerson (filter #(= (:Id %) currentId) fakeData/PERSONS)]
    (if (not= (count currentPerson) 0)
      {:status 200 :headers {"Content-Type" "application/json"} :body currentPerson}
      {:status 404 :body "Person not exist"}))))

(defn delete [request]
  (let [currentId (get-in request [:path-params :id])]
    (let [newPersons (filter #(not= (:Id %) currentId) fakeData/PERSONS)]
      (do (alter-var-root (var fakeData/PERSONS) (constantly newPersons))
          {:status 200 :body "Person deleted successfully"}))))

(defn create [request]
  (let [body (-> request :body slurp)
      newPerson (parse-string body true)]
  (if-let [person-field (validate-person-fields newPerson)]
      {:status 404 :body person-field }
    (do
      (def newPersonWithId (assoc newPerson :Id (functions/generate-random-id)))
      (alter-var-root #'fakeData/PERSONS conj newPersonWithId)
        {:status 200 :body "Stored successfully!"}))))

(defn update [request]
  (let [currentId (get-in request [:path-params :id])
        indexToUpdate (find-index-by-id fakeData/PERSONS currentId)]
    (if (not= indexToUpdate nil)
      (let [body (-> request :body slurp)
            newPerson (parse-string body true)
            newPersonWithId (assoc newPerson :Id currentId)]
        (if-let [person-field (validate-person-fields newPersonWithId)]
          {:status 404 :body person-field }
          (do
            (alter-var-root #'fakeData/PERSONS
              (fn [persons]
                (assoc persons indexToUpdate newPersonWithId))) 
            {:status 200 :body "Person updated"})))
      {:status 404 :body "Person not found"})))

