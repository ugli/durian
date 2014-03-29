package se.ugli.durian.j.dom.query;

import java.io.IOException;
import java.io.InputStream;

import se.ugli.durian.j.dom.query.simple.SimpleQueryEngine;

class QueryEngineFactory {

    final static String SPI_RESOURCE_PATH = "/META-INF/services/se.ugli.durian.j.dom.query.engine";

    private static QueryEngine queryEngine;

    private QueryEngineFactory() {
    }

    static QueryEngine create() {
        if (queryEngine == null) {
            queryEngine = createFromSpi();
        }
        return queryEngine;
    }

    static void setQueryEngine(final QueryEngine queryEngine) {
        QueryEngineFactory.queryEngine = queryEngine;
    }

    private static QueryEngine createFromSpi() {
        final InputStream classNameStream = QueryEngineFactory.class.getResourceAsStream(SPI_RESOURCE_PATH);
        if (classNameStream != null) {
            try {
                final byte[] bytes = new byte[classNameStream.available()];
                classNameStream.read(bytes);
                final String className = new String(bytes);
                return (QueryEngine) Class.forName(className).newInstance();
            }
            catch (final IOException e) {
                throw new QueryEngineException(e);
            }
            catch (final InstantiationException e) {
                throw new QueryEngineException(e);
            }
            catch (final IllegalAccessException e) {
                throw new QueryEngineException(e);
            }
            catch (final ClassNotFoundException e) {
                throw new QueryEngineException(e);
            }
            finally {
                try {
                    classNameStream.close();
                }
                catch (final IOException e) {
                    throw new QueryEngineException(e);
                }
            }
        }
        return new SimpleQueryEngine();
    }
}
