package core.coreUI.ui.initialDriver;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static core.helper.Base.TIME_OUT;

public class DriverWebElement implements WebDriver, JavascriptExecutor, TakesScreenshot, WrapsDriver, Interactive, HasCapabilities {
    private final WebDriver driver;
    private final ArrayList<WebDriverEventListener> eventListeners = new ArrayList<>();
    private final WebDriverEventListener dispatcher = (WebDriverEventListener) Proxy.newProxyInstance(WebDriverEventListener.class.getClassLoader(), new Class[]{WebDriverEventListener.class}, (proxy, method, args) -> {
        try {
            for (final WebDriverEventListener listener : this.eventListeners) {
                method.invoke(listener, args);
            }

            return null;
        } catch (InvocationTargetException var6) {
            throw var6.getTargetException();
        }
    });

    public DriverWebElement(WebDriver driver) {
        Class<?>[] allInterfaces = this.extractInterfaces(driver);
        this.driver = (WebDriver) Proxy.newProxyInstance(WebDriverEventListener.class.getClassLoader(), allInterfaces, (proxy, method, args) -> {
            if ("getWrappedDriver".equals(method.getName())) {
                return driver;
            } else {
                try {
                    return method.invoke(driver, args);
                } catch (InvocationTargetException var6) {
                    this.dispatcher.onException(var6.getTargetException(), driver);
                    throw var6.getTargetException();
                }
            }
        });
    }

    private Class<?>[] extractInterfaces(org.openqa.selenium.SearchContext object) {
        HashSet allInterfaces = new HashSet();
        allInterfaces.add(WrapsDriver.class);
        if (object instanceof WebElement) {
            allInterfaces.add(WrapsElement.class);
        }

        this.extractInterfaces(allInterfaces, object.getClass());
        return (Class[]) allInterfaces.toArray(new Class[allInterfaces.size()]);
    }

    private void extractInterfaces(Set<Class<?>> addTo, Class<?> clazz) {
        if (!Object.class.equals(clazz)) {
            Class<?>[] classes = clazz.getInterfaces();
            addTo.addAll(Arrays.asList(classes));
            this.extractInterfaces(addTo, clazz.getSuperclass());
        }
    }

    public DriverWebElement register(WebDriverEventListener eventListener) {
        this.eventListeners.add(eventListener);
        return this;
    }

    public DriverWebElement unregister(WebDriverEventListener eventListener) {
        this.eventListeners.remove(eventListener);
        return this;
    }

    public WebDriver getWrappedDriver() {
        return this.driver instanceof WrapsDriver ? ((WrapsDriver) this.driver).getWrappedDriver() : this.driver;
    }

    public void get(String url) {
        this.dispatcher.beforeNavigateTo(url, this.driver);
        this.driver.get(url);
        this.dispatcher.afterNavigateTo(url, this.driver);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        this.dispatcher.beforeFindBy(by, null, this.driver);
        List<WebElement> temp = this.driver.findElements(by);
        this.dispatcher.afterFindBy(by, null, this.driver);
        List<WebElement> result = new ArrayList<>(temp.size());

        for (final WebElement element : temp) {
            result.add(this.createWebElement(element));
        }

        return result;
    }

    public WebElement findElement(By by) {
        this.dispatcher.beforeFindBy(by, null, this.driver);
        WebElement temp = this.driver.findElement(by);
        this.dispatcher.afterFindBy(by, temp, this.driver);
        return this.createWebElement(temp);
    }

    public String getPageSource() {
        return this.driver.getPageSource();
    }

    public void close() {
        this.driver.close();
    }

    public void quit() {
       this.driver.quit();
    }

    public Set<String> getWindowHandles() {
        return this.driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }


    @Override
    public Object executeScript(final String s, final Object... objects) {
        return null;
    }

    public Object executeAsyncScript(String script, Object... args) {
        if (this.driver instanceof JavascriptExecutor) {
            this.dispatcher.beforeScript(script, this.driver);
            Object[] usedArgs = this.unpackWrappedArgs(args);
            Object result = ((JavascriptExecutor) this.driver).executeAsyncScript(script, usedArgs);
            this.dispatcher.afterScript(script, this.driver);
            return result;
        } else {
            throw new UnsupportedOperationException("Underlying driver instance does not support executing javascript");
        }
    }

    private Object[] unpackWrappedArgs(Object... args) {
        Object[] usedArgs = new Object[args.length];

        for (int i = 0; i < args.length; ++i) {
            usedArgs[i] = this.unpackWrappedElement(args[i]);
        }

        return usedArgs;
    }

    private Object unpackWrappedElement(Object arg) {
        Iterator var4;
        Object key;
        if (arg instanceof List) {
            List<?> aList = (List) arg;
            ArrayList toReturn = new ArrayList();
            var4 = aList.iterator();

            while (var4.hasNext()) {
                key = var4.next();
                toReturn.add(this.unpackWrappedElement(key));
            }

            return toReturn;
        } else if (!(arg instanceof Map)) {
            return arg instanceof DriverWebElement.EventFiringWebElement ? ((DriverWebElement.EventFiringWebElement) arg).getWrappedElement() : arg;
        } else {
            Map<?, ?> aMap = (Map) arg;
            HashMap toReturn = new HashMap();
            var4 = aMap.keySet().iterator();

            while (var4.hasNext()) {
                key = var4.next();
                toReturn.put(key, this.unpackWrappedElement(aMap.get(key)));
            }

            return toReturn;
        }
    }


    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        if (this.driver instanceof TakesScreenshot) {
            this.dispatcher.beforeGetScreenshotAs(target);
            X screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(target);
            this.dispatcher.afterGetScreenshotAs(target, screenshot);
            return screenshot;
        } else {
            throw new UnsupportedOperationException("Underlying driver instance does not support taking screenshots");
        }
    }

    public TargetLocator switchTo() {
        return new DriverWebElement.EventFiringTargetLocator(this.driver.switchTo());
    }

    public Navigation navigate() {
        return new DriverWebElement.EventFiringNavigation(this.driver.navigate());
    }

    public Options manage() {
        return new DriverWebElement.EventFiringOptions(this.driver.manage());
    }

    private WebElement createWebElement(WebElement from) {
        return new DriverWebElement.EventFiringWebElement(from);
    }

    public void perform(Collection<Sequence> actions) {
        if (this.driver instanceof Interactive) {
            ((Interactive) this.driver).perform(actions);
        } else {
            throw new UnsupportedOperationException("Underlying driver does not implement advanced user interactions yet.");
        }
    }

    public void resetInputState() {
        if (this.driver instanceof Interactive) {
            ((Interactive) this.driver).resetInputState();
        } else {
            throw new UnsupportedOperationException("Underlying driver does not implement advanced user interactions yet.");
        }
    }

    public Capabilities getCapabilities() {
        if (this.driver instanceof HasCapabilities) {
            return ((HasCapabilities) this.driver).getCapabilities();
        } else {
            throw new UnsupportedOperationException("Underlying driver does not implement getting capabilities yet.");
        }
    }

    private static class EventFiringTimeouts implements Timeouts {
        private final Timeouts timeouts;

        EventFiringTimeouts(Timeouts timeouts) {
            this.timeouts = timeouts;
        }

        public Timeouts implicitlyWait(long time, TimeUnit unit) {
            this.timeouts.implicitlyWait(time, unit);
            return this;
        }

        public Timeouts setScriptTimeout(long time, TimeUnit unit) {
            this.timeouts.setScriptTimeout(time, unit);
            return this;
        }

        public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
            this.timeouts.pageLoadTimeout(time, unit);
            return this;
        }
    }

    private class EventFiringAlert implements Alert {
        private final Alert alert;

        private EventFiringAlert(Alert alert) {
            this.alert = alert;
        }

        public void dismiss() {
            DriverWebElement.this.dispatcher.beforeAlertDismiss(DriverWebElement.this.driver);
            this.alert.dismiss();
            DriverWebElement.this.dispatcher.afterAlertDismiss(DriverWebElement.this.driver);
        }

        public void accept() {
            DriverWebElement.this.dispatcher.beforeAlertAccept(DriverWebElement.this.driver);
            this.alert.accept();
            DriverWebElement.this.dispatcher.afterAlertAccept(DriverWebElement.this.driver);
        }

        public String getText() {
            return this.alert.getText();
        }

        public void sendKeys(String keysToSend) {
            this.alert.sendKeys(keysToSend);
        }
    }

    @Beta
    private class EventFiringWindow implements Window {
        private final Window window;

        EventFiringWindow(Window window) {
            this.window = window;
        }

        public Dimension getSize() {
            return this.window.getSize();
        }

        public void setSize(Dimension targetSize) {
            this.window.setSize(targetSize);
        }

        public Point getPosition() {
            return this.window.getPosition();
        }

        public void setPosition(Point targetLocation) {
            this.window.setPosition(targetLocation);
        }

        public void maximize() {
            this.window.maximize();
        }

        public void fullscreen() {
            this.window.fullscreen();
        }
    }

    private class EventFiringTargetLocator implements TargetLocator {
        private final TargetLocator targetLocator;

        private EventFiringTargetLocator(TargetLocator targetLocator) {
            this.targetLocator = targetLocator;
        }

        public WebDriver frame(int frameIndex) {
            return this.targetLocator.frame(frameIndex);
        }

        public WebDriver frame(String frameName) {
            return this.targetLocator.frame(frameName);
        }

        public WebDriver frame(WebElement frameElement) {
            return this.targetLocator.frame(frameElement);
        }

        public WebDriver parentFrame() {
            return this.targetLocator.parentFrame();
        }

        public WebDriver window(String windowName) {
            DriverWebElement.this.dispatcher.beforeSwitchToWindow(windowName, DriverWebElement.this.driver);
            WebDriver driverToReturn = this.targetLocator.window(windowName);
            DriverWebElement.this.dispatcher.afterSwitchToWindow(windowName, DriverWebElement.this.driver);
            return driverToReturn;
        }

        public WebDriver defaultContent() {
            return this.targetLocator.defaultContent();
        }

        public WebElement activeElement() {
            return this.targetLocator.activeElement();
        }

        public Alert alert() {
            return DriverWebElement.this.new EventFiringAlert(this.targetLocator.alert());
        }
    }

    private class EventFiringOptions implements Options {
        private final Options options;

        private EventFiringOptions(Options options) {
            this.options = options;
        }

        public Logs logs() {
            return this.options.logs();
        }

        public void addCookie(Cookie cookie) {
            this.options.addCookie(cookie);
        }

        public void deleteCookieNamed(String name) {
            this.options.deleteCookieNamed(name);
        }

        public void deleteCookie(Cookie cookie) {
            this.options.deleteCookie(cookie);
        }

        public void deleteAllCookies() {
            this.options.deleteAllCookies();
        }

        public Set<Cookie> getCookies() {
            return this.options.getCookies();
        }

        public Cookie getCookieNamed(String name) {
            return this.options.getCookieNamed(name);
        }

        public Timeouts timeouts() {
            return new EventFiringTimeouts(this.options.timeouts());
        }

        public ImeHandler ime() {
            return this.options.ime();
        }

        @Beta
        public Window window() {
            return DriverWebElement.this.new EventFiringWindow(this.options.window());
        }
    }

    private class EventFiringNavigation implements Navigation {
        private final Navigation navigation;

        EventFiringNavigation(Navigation navigation) {
            this.navigation = navigation;
        }

        public void to(String url) {
            DriverWebElement.this.dispatcher.beforeNavigateTo(url, DriverWebElement.this.driver);
            this.navigation.to(url);
            DriverWebElement.this.dispatcher.afterNavigateTo(url, DriverWebElement.this.driver);
        }

        public void to(URL url) {
            this.to(String.valueOf(url));
        }

        public void back() {
            DriverWebElement.this.dispatcher.beforeNavigateBack(DriverWebElement.this.driver);
            this.navigation.back();
            DriverWebElement.this.dispatcher.afterNavigateBack(DriverWebElement.this.driver);
        }

        public void forward() {
            DriverWebElement.this.dispatcher.beforeNavigateForward(DriverWebElement.this.driver);
            this.navigation.forward();
            DriverWebElement.this.dispatcher.afterNavigateForward(DriverWebElement.this.driver);
        }

        public void refresh() {
            DriverWebElement.this.dispatcher.beforeNavigateRefresh(DriverWebElement.this.driver);
            this.navigation.refresh();
            DriverWebElement.this.dispatcher.afterNavigateRefresh(DriverWebElement.this.driver);
        }
    }

    private class EventFiringWebElement implements WebElement, WrapsElement, WrapsDriver, Locatable {
        private final WebElement element;
        private final WebElement underlyingElement;

        private EventFiringWebElement(WebElement element) {
            this.element = (WebElement) Proxy.newProxyInstance(WebDriverEventListener.class.getClassLoader(), DriverWebElement.this.extractInterfaces(element), (proxy, method, args) -> {
                if (method.getName().equals("getWrappedElement")) {
                    return element;
                } else {
                    try {
                        return method.invoke(element, args);
                    } catch (InvocationTargetException var6) {
                        DriverWebElement.this.dispatcher.onException(var6.getTargetException(), DriverWebElement.this.driver);
                        throw var6.getTargetException();
                    }
                }
            });
            this.underlyingElement = element;
        }

        public void click() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, TIME_OUT);
            long endTime = calendar.getTimeInMillis();
            calendar.clear();
            do {
                if (new Date().getTime() > endTime)
                    return;
                try {
                    if (element.isEnabled() && element.isDisplayed()) {
                        DriverWebElement.this.dispatcher.beforeClickOn(this.element, DriverWebElement.this.driver);
                        this.element.click();
                        DriverWebElement.this.dispatcher.afterClickOn(this.element, DriverWebElement.this.driver);
                        return;
                    } else {
                        click();
                    }
                } catch (StaleElementReferenceException | ElementNotInteractableException var4) {
                    System.err.println("MSG Exception : " + var4.getMessage() + " Element : " + element);
                } catch (NoSuchElementException var4) {
                    System.err.println("MSG No Such Element exception : " + var4.getMessage() + " Element : " + element);
                    return;
                }
            }
            while (true);
        }

        public void submit() {
            this.element.submit();
        }

        public void sendKeys(CharSequence... keysToSend) {
            DriverWebElement.this.dispatcher.beforeChangeValueOf(this.element, DriverWebElement.this.driver, keysToSend);
            this.element.sendKeys(keysToSend);
            DriverWebElement.this.dispatcher.afterChangeValueOf(this.element, DriverWebElement.this.driver, keysToSend);
        }

        public void clear() {
            DriverWebElement.this.dispatcher.beforeChangeValueOf(this.element, DriverWebElement.this.driver, null);
            this.element.clear();
            DriverWebElement.this.dispatcher.afterChangeValueOf(this.element, DriverWebElement.this.driver, null);
        }

        public String getTagName() {
            return this.element.getTagName();
        }

        public String getAttribute(String name) {
            return this.element.getAttribute(name);
        }

        public boolean isSelected() {
            return this.element.isSelected();
        }

        public boolean isEnabled() {
            return this.element.isEnabled();
        }

        public String getText() {
            DriverWebElement.this.dispatcher.beforeGetText(this.element, DriverWebElement.this.driver);
            String text = this.element.getText();
            DriverWebElement.this.dispatcher.afterGetText(this.element, DriverWebElement.this.driver, text);
            return text;
        }

        public boolean isDisplayed() {
            return this.element.isDisplayed();
        }

        public Point getLocation() {
            return this.element.getLocation();
        }

        public Dimension getSize() {
            return this.element.getSize();
        }

        public Rectangle getRect() {
            return this.element.getRect();
        }

        public String getCssValue(String propertyName) {
            return this.element.getCssValue(propertyName);
        }

        public WebElement findElement(By by) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, TIME_OUT);
            long endTime = calendar.getTimeInMillis();
            calendar.clear();
            do {
                try {
                    DriverWebElement.this.dispatcher.beforeFindBy(by, this.element, DriverWebElement.this.driver);
                    WebElement temp = this.element.findElement(by);
                    DriverWebElement.this.dispatcher.afterFindBy(by, this.element, DriverWebElement.this.driver);
                    return DriverWebElement.this.createWebElement(temp);
                } catch (StaleElementReferenceException | NoSuchElementException | ElementClickInterceptedException var4) {
                    //System.err.println("MSG Exception : " + var4.getMessage() + " Element : " + element);
                }
            }
            while (new Date().getTime() < endTime);
            return null;
        }

        public List<WebElement> findElements(By by) {
            DriverWebElement.this.dispatcher.beforeFindBy(by, this.element, DriverWebElement.this.driver);
            List<WebElement> temp = this.element.findElements(by);
            DriverWebElement.this.dispatcher.afterFindBy(by, this.element, DriverWebElement.this.driver);
            List<WebElement> result = new ArrayList<>(temp.size());
            for (final WebElement element : temp) {
                result.add(DriverWebElement.this.createWebElement(element));
            }

            return result;
        }

        public WebElement getWrappedElement() {
            return this.underlyingElement;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WebElement)) {
                return false;
            } else {
                WebElement other = (WebElement) obj;
                if (other instanceof WrapsElement) {
                    other = ((WrapsElement) other).getWrappedElement();
                }

                return this.underlyingElement.equals(other);
            }
        }

        public int hashCode() {
            return this.underlyingElement.hashCode();
        }

        public String toString() {
            return this.underlyingElement.toString();
        }

        public WebDriver getWrappedDriver() {
            return DriverWebElement.this.driver;
        }

        public Coordinates getCoordinates() {
            return ((Locatable) this.underlyingElement).getCoordinates();
        }

        public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
            return this.element.getScreenshotAs(outputType);
        }
    }
}
