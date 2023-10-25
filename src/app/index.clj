(ns app.index
  (:require [io.pedestal.http :as http]
            [app.routes :as routes]))

(defn create-server []
  (http/create-server
   {::http/routes routes/routes
    ::http/type   :jetty
    ::http/port   3000}))

(defn start []
  (http/start (create-server)))
