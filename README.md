[![Clojars Project](https://img.shields.io/clojars/v/jtk-dvlp/re-frame-worker-fx.svg)](https://clojars.org/jtk-dvlp/re-frame-worker-fx)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/jtkDvlp/re-frame-worker-fx/blob/master/LICENSE)
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/donate?hosted_button_id=2PDXQMHX56T6U)

# Web workers effect handler for re-frame

This [re-frame](https://github.com/Day8/re-frame) library contains an [web worker](https://developer.mozilla.org/docs/Web/API/Web_Workers_API/Using_web_workers) [effect handler](https://github.com/Day8/re-frame/tree/develop/docs). The handler can be addressed by `:worker` and wraps the API of [cljs-workers](https://github.com/jtkDvlp/cljs-workers).

## Getting started

### Get it / add dependency

Add the following dependency to your `project.cljs`:<br>
[![Clojars Project](https://img.shields.io/clojars/v/jtk-dvlp/re-frame-worker-fx.svg)](https://clojars.org/jtk-dvlp/re-frame-worker-fx)

### Usage

See the following minimal code example or the [test.cljs](https://github.com/jtkDvlp/re-frame-worker-fx/blob/master/test/re_frame_worker_fx/test.cljs). For general usage of workers see [cljs-workers](https://github.com/jtkDvlp/cljs-workers).

The following example presupposes that there is already a worker pool and registered worker-handler.

```clojure
(ns your.project
  (:require [re-frame.core :as re-frame]
            [re-frame-worker-fx.core]))

(re-frame/reg-event-fx
 :some-event
 (fn [coeffects _]
   (let [worker-pool
         (-> coeffects :db :worker-pool)]
     {:worker {:pool worker-pool
               ;; handler that will be called
               :handler :your-worker-handler
               ;; arguments applied to the handler
               :arguments {:a "Hallo Welt!" :b 10 :c (js/ArrayBuffer. 10)}
               ;; which arguments will be transfered instead of copied
               :transfer [:c]
               ;; dispatched on success conjoined with the result
               :on-success [:your-success-event]
               ;; dispatched on error conjoined with the result
               :on-error [:your-error-event]}})))
```

## Appendix

I´d be thankful to receive patches, comments and constructive criticism.

Hope the package is useful :-)
