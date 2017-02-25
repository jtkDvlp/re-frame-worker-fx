(ns re-frame-worker-fx.core
  (:require [re-frame.core :refer [reg-fx dispatch]]
            [cljs-workers.core :refer [do-with-pool!]]))

(reg-fx
 :worker
 (fn worker-fx
   [{:keys [pool on-success on-error] :as data}]
   (let [on-result
         (fn [{:keys [state] :as result}]
           (if (= :success (keyword state))
             (dispatch (conj on-success result))
             (dispatch (conj on-error result))))]
     (do-with-pool! pool data on-result))))
