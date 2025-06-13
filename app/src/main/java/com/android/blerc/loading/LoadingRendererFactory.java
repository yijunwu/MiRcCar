package com.android.blerc.loading;

import android.content.Context;
import android.util.SparseArray;
import java.lang.reflect.Constructor;

/* loaded from: classes.dex */
public final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS;

    static {
        SparseArray<Class<? extends LoadingRenderer>> sparseArray = new SparseArray<>();
        LOADING_RENDERERS = sparseArray;
        sparseArray.put(7, CollisionLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(Context context, int i) throws Exception {
        for (Constructor<?> constructor : LOADING_RENDERERS.get(i).getDeclaredConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length == 1 && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }
        throw new InstantiationException();
    }
}
