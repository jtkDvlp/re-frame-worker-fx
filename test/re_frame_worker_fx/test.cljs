(ns re-frame-worker-fx.test
  (:require [cljs-workers.core :as main]
            [cljs-workers.worker :as worker]
            [re-frame.core :as re-frame]
            [re-frame-worker-fx.core]))

(defn app
  []
  (re-frame/reg-event-fx
   :on-worker-fx-success
   (fn [_ [_ result]]
     (.debug js/console "success" result)))

  (re-frame/reg-event-fx
   :on-worker-fx-error
   (fn [_ [_ result]]
     (.debug js/console "error" result)))

  (re-frame/reg-event-fx
   :test-worker-fx
   (fn [coeffects [_ task]]
     (let [worker-pool
           (-> coeffects :db :worker-pool)

           task-with-pool
           (assoc task :pool worker-pool)]

       {:worker task-with-pool})))

  (re-frame/reg-event-fx
   :initialize
   (fn [{:keys [db]} _]
     {:db {:worker-pool (main/create-pool 2 "js/worker/worker.js")}
      :dispatch-n [[:test-worker-fx {:handler :mirror, :arguments {:a "Hallo" :b "Welt" :c 10} :on-success [:on-worker-fx-success] :on-error [:on-worker-fx-error]}]
                   [:test-worker-fx {:handler :mirror, :arguments {:a "Hallo" :b "Welt" :c 10 :d (js/ArrayBuffer. 10) :transfer [:d]} :transfer [:d] :on-success [:on-worker-fx-success] :on-error [:on-worker-fx-error]}]
                   [:test-worker-fx {:handler :mirror, :arguments {:a "Hallo" :b "Welt" :c 10 :d (js/ArrayBuffer. 10) :transfer [:d]} :transfer [:c] :on-success [:on-worker-fx-success] :on-error [:on-worker-fx-error]}]
                   [:test-worker-fx {:handler :mirror, :arguments {:a "Hallo" :b "Welt" :c 10 :d (js/ArrayBuffer. 10) :transfer [:c]} :transfer [:d] :on-success [:on-worker-fx-success] :on-error [:on-worker-fx-error]}]]}))

  (re-frame/dispatch-sync [:initialize]))

(defn worker
  []
  (worker/register
   :mirror
   (fn [arguments]
     arguments))

  (worker/bootstrap))

(if (and (main/supported?) (main/main?))
  (app)
  (worker))
