(ns app.routes
    (:require [io.pedestal.http.route :as route]
              [app.controllers.persons :as persons]))

(def routes
  (route/expand-routes
   #{["/persons" :get persons/get-all :route-name :persons]
     ["/create-person" :post persons/create :route-name :create]
     ["/person/:id/delete" :delete persons/delete :route-name :delete]
     ["/person/:id/update" :put persons/update :route-name :update]
     ["/person/:id" :get persons/get-by-id :route-name :person]}))