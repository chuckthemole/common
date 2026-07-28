package com.rumpus.common.Config.Model;

import org.springframework.context.annotation.Bean;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Service.ISerializerService;
import com.rumpus.common.Service.SerializerService;

/**
 * Base configuration for model-related services.
 *
 * <p>
 * Concrete applications extend this class to provide:
 * <ul>
 * <li>A model service manager</li>
 * <li>A serializer registry</li>
 * </ul>
 *
 * <p>
 * This base configuration exposes common Spring beans while allowing subclasses
 * to supply application-specific implementations.
 *
 * @param <SERVICE_MANAGER>
 *            Concrete {@link AbstractServiceManager} implementation.
 * @param <SERIALIZER_REGISTRY>
 *            Concrete {@link ISerializerRegistry} implementation.
 */
public abstract class AbstractCommonModelConfig<SERVICE_MANAGER extends AbstractServiceManager<?>,
        SERIALIZER_REGISTRY extends ISerializerRegistry>
        extends
            AbstractCommonObject {

    /**
     * Bean name for the application's service manager.
     */
    public static final String BEAN_MODEL_SERVICES = "modelServices";

    /**
     * Bean name for the serializer registry.
     */
    public static final String BEAN_SERIALIZER_REGISTRY = "modelSerializerRegistry";

    /**
     * Bean name for the serializer service.
     */
    public static final String BEAN_SERIALIZER_SERVICE = "modelSerializerService";

    /**
     * Ctor
     */
    protected AbstractCommonModelConfig() {
    }

    /**
     * Creates the application's service manager.
     *
     * @return configured service manager
     */
    @Bean(name = BEAN_MODEL_SERVICES)
    public SERVICE_MANAGER modelServices() {
        return this.createModelServices();
    }

    /**
     * Creates the serializer registry used by the application.
     *
     * @return configured serializer registry
     */
    @Bean(name = BEAN_SERIALIZER_REGISTRY)
    public SERIALIZER_REGISTRY modelSerializerRegistry() {
        return this.createSerializerRegistry();
    }

    /**
     * Creates the serializer service.
     *
     * <p>
     * The serializer service wraps the application's serializer registry and
     * provides higher-level serialization operations.
     *
     * @param serializerRegistry
     *            injected serializer registry bean
     *
     * @return configured serializer service
     */
    @Bean(name = BEAN_SERIALIZER_SERVICE)
    public ISerializerService serializerService(
            SERIALIZER_REGISTRY serializerRegistry) {

        return new SerializerService(serializerRegistry);
    }

    /**
     * Creates the application's service manager.
     *
     * @return concrete service manager
     */
    protected abstract SERVICE_MANAGER createModelServices();

    /**
     * Creates the serializer registry for the application.
     *
     * @return concrete serializer registry
     */
    protected abstract SERIALIZER_REGISTRY createSerializerRegistry();
}
