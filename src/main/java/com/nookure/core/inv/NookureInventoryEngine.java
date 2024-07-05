package com.nookure.core.inv;

import com.nookure.core.inv.parser.GuiLayout;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.loader.FileLoader;
import io.pebbletemplates.pebble.loader.Loader;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class NookureInventoryEngine {
  private final PebbleEngine engine;
  private final Loader<?> loader;
  private final JAXBContext context;

  {
    try {
      context = JAXBContext.newInstance(GuiLayout.class);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private final Unmarshaller unmarshaller;

  {
    try {
      unmarshaller = context.createUnmarshaller();
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private final SAXParserFactory spf;

  {
    spf = SAXParserFactory.newInstance();
    try {
      spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
      spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      spf.setXIncludeAware(false);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Constructor for NookureInventoryEngine
   *
   * @param loader The loader to use for the engine
   */
  public NookureInventoryEngine(Loader<?> loader, AbstractExtension... extensions) {
    this.loader = loader;

    this.engine = new PebbleEngine.Builder()
        .loader(loader)
        .extension(extensions)
        .build();
  }

  /**
   * Constructor for NookureInventoryEngine
   *
   * @param path The path to use for the FileLoader
   *             This loader uses the FileSystem to load templates
   *             instead of the classpath
   */
  public NookureInventoryEngine(Path path, AbstractExtension... extensions) throws JAXBException {
    this(getLoaderByPath(path), extensions);
  }

  @NotNull
  private static FileLoader getLoaderByPath(@NotNull Path path) {
    requireNonNull(path, "path cannot be null");
    FileLoader loader = new FileLoader();
    loader.setPrefix(path.toString());

    return loader;
  }

  /**
   * Renders a template with the given context
   * <p>
   * Here is an example of how to use this method:
   * </p>
   * <pre>
   *     NookureInventoryEngine engine = new NookureInventoryEngine(Path.of("/path/to/templates"));
   *
   *     engine.renderTemplate("template.html", "name", "Angelillo15", "age", "16");
   * </pre>
   *
   * @param templateName The name of the template to render
   * @param context      The context to render the template with
   *                     This must be key-value pairs
   *                     The keys must be strings
   *                     The values can be any object
   *                     The context will be passed to the template
   *                     as variables
   * @return The rendered template
   * @throws IllegalArgumentException If the context is not key-value pairs
   *                                  or the keys are not strings
   */
  public String renderTemplate(@NotNull String templateName, @NotNull Object... context) {
    return renderTemplate(templateName, toMap(context));
  }

  /**
   * Renders a template with the given context
   *
   * @param templateName The name of the template to render
   * @param context      The context to render the template with
   * @return The rendered template
   */
  public String renderTemplate(@NotNull String templateName, @NotNull Map<String, Object> context) {
    Writer writer = new StringWriter();

    requireNonNull(templateName, "templateName cannot be null");
    PebbleTemplate template = engine.getTemplate(templateName);

    try {
      template.evaluate(writer, context);
    } catch (Exception e) {
      throw new RuntimeException("Failed to render template", e);
    }

    return writer.toString();
  }

  /**
   * Parses a layout from a template
   *
   * @param templateName The name of the template to parse
   * @param context      The context to parse the template with
   * @return The parsed layout
   * @throws JAXBException    If the layout could not be parsed
   * @throws RuntimeException If the layout could not be parsed
   */
  public GuiLayout parseLayout(@NotNull String templateName, @NotNull Map<String, Object> context) throws JAXBException, ParserConfigurationException, SAXException {
    String renderedTemplate = renderTemplate(templateName, context);
    StringReader reader = new StringReader(renderedTemplate);
    InputSource inputSource = new InputSource(reader);
    SAXSource saxSource = new SAXSource(spf.newSAXParser().getXMLReader(), inputSource);

    synchronized (unmarshaller) {
      if (unmarshaller.unmarshal(saxSource) instanceof GuiLayout guiLayout) {
        return guiLayout;
      }
    }

    throw new RuntimeException("Failed to parse layout");
  }

  /**
   * Renders a template with the given context and parses it into a GuiLayout
   *
   * <p>
   * Here is an example of how to use this method:
   * </p>
   * <pre>
   *     NookureInventoryEngine engine = new NookureInventoryEngine(Path.of("/path/to/templates"));
   *
   *     engine.parseLayout("template.html", "name", "Angelillo15", "age", "16");
   * </pre>
   *
   * @param templateName The name of the template to render
   * @param context      The context to render the template with
   *                     This must be key-value pairs
   *                     The keys must be strings
   *                     The values can be any object
   *                     The context will be passed to the template
   *                     as variables
   * @return The rendered template as a GuiLayout
   * @throws IllegalArgumentException If the context is not key-value pairs
   *                                  or the keys are not strings
   */
  public GuiLayout parseLayout(@NotNull String templateName, @NotNull Object... context) throws JAXBException, ParserConfigurationException, SAXException {
    return parseLayout(templateName, toMap(context));
  }


  public static Map<String, Object> toMap(Object... context) {
    if (context.length % 2 != 0) {
      throw new IllegalArgumentException("Context must be key-value pairs");
    }

    for (int i = 0; i < context.length; i += 2) {
      if (!(context[i] instanceof String)) {
        throw new IllegalArgumentException("Context keys must be strings");
      }
    }

    Map<String, Object> contextMap = new HashMap<>();

    for (int i = 0; i < context.length; i += 2) {
      contextMap.put((String) context[i], context[i + 1]);
    }

    return contextMap;
  }

  public static Object[] toContextObjectArray(Map<String, Object> context) {
    Object[] contextArray = new Object[context.size() * 2];
    int i = 0;

    for (Map.Entry<String, Object> entry : context.entrySet()) {
      contextArray[i] = entry.getKey();
      contextArray[i + 1] = entry.getValue();
      i += 2;
    }

    return contextArray;
  }

  /**
   * Gets the PebbleEngine used by this NookureInventoryEngine
   *
   * @return The PebbleEngine used by this NookureInventoryEngine
   */
  public PebbleEngine getEngine() {
    return engine;
  }

  /**
   * Gets the loader used by this NookureInventoryEngine
   *
   * @return The loader used by this NookureInventoryEngine
   */
  public Loader<?> getLoader() {
    return loader;
  }

  public JAXBContext getContext() {
    return context;
  }

  public Unmarshaller getUnmarshaller() {
    return unmarshaller;
  }

  public SAXParserFactory getSAXParserFactory() {
    return spf;
  }
}
