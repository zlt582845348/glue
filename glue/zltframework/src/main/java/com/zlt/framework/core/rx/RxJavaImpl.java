package com.zlt.framework.core.rx;

import com.zlt.framework.core.coreInterface.IRx;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/22.
 */

public class RxJavaImpl implements IRx {
    
    private Observable observable;

    @Override
    public IRx publish(final Object... objs) {
        observable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                for (Object obj : objs)
                {
                    subscriber.onNext(obj);
                }
                subscriber.onCompleted();
            }
        });
        return this;
    }

    @Override
    public IRx bind(final IRxCallback cb) {
        //创建一个观察者
        Subscriber<Object> subscriber = new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                cb.next(IRxCallback.TYPE_COMPLETED,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                cb.next(IRxCallback.TYPE_ERROR,e);
            }

            @Override
            public void onNext(Object obj) {
                cb.next(IRxCallback.TYPE_NEXT,obj);
            }
        };
        //注册观察者(这个方法名看起来有点怪，还不如写成regisiterSubscriber(..)或者干脆addSubscriber(..))
        //注册后就会开始调用call()中的观察者执行的方法 onNext() onCompleted()等
        observable.subscribe(subscriber);
        return this;
    }
}
