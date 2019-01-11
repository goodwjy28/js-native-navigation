//
//  BotsJSCoreBaseViewController.m
//  BotsItoa
//
//  Created by 刘海川 on 2018/12/21.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import "HCJSCoreBaseViewController.h"
#import "HCUIJSExportImpl.h"
#import "HCNavigationJSExportImpl.h"
#import "NSObject+HCJSContext.h"

@interface HCJSCoreBaseViewController ()

@property (nonatomic, strong, readwrite) JSContext *appJSContext;

@end

@implementation HCJSCoreBaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self hc_registerCreateJSContextNotification];
}

- (void)dealloc {
    [self hc_unregisterCreateJSContextNotification];
}

- (void)executeJSValueThreadSafe:(JSValue *)value args:(NSArray *)args {
    if (value) {
        NSArray *args2 = args ? args : @[];
        NSArray *params = @[value, args2];
        if (self.jsThread) {
            [self performSelector:@selector(callJS:)
                         onThread:self.jsThread
                       withObject:params
                    waitUntilDone:NO];
        } else {
            [value callWithArguments:args];
        }
    }
}

- (JSValue *)callJsBridge:(NSString *)methodName args:(NSArray *)args {
    JSValue * jsBridge = self.appJSContext[@"$jsBridge"];
    JSValue *jsFunction = [jsBridge valueForProperty:methodName];
    return [jsFunction callWithArguments:args];
}

- (void)callJS:(NSArray *)params {
    if ([params count] != 2) return;
    JSValue *value = params[0];
    NSArray *args = params[1];
    [value callWithArguments:args];
}

#pragma mark - Private Methods
- (void)hc_registerCreateJSContextNotification {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(didCreateJSContext:)
                                                 name:HCDidCreateJSContextNotification
                                               object:nil];
}

- (void)hc_unregisterCreateJSContextNotification {
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:HCDidCreateJSContextNotification
                                                  object:nil];
}

- (void)hc_injectJSExportImpl {
    self.appJSContext[@"NativeUI"] = [HCUIJSExportImpl instance:self];
    [self injectJSExportImpl:self.appJSContext];
}

#pragma mark - Notification Selector
- (void)didCreateJSContext:(NSNotification *)notification {
    NSString *indentifier = [NSString stringWithFormat:@"indentifier%lud", (unsigned long)[self getWebView].hash];
    NSString *indentifierJS = [NSString stringWithFormat:@"var %@ = '%@'", indentifier, indentifier];
    [[self getWebView] stringByEvaluatingJavaScriptFromString:indentifierJS];
    JSContext *context = notification.object;
    //判断这个context是否属于当前这个webView
    if (![context[indentifier].toString isEqualToString:indentifier]) return;
    self.appJSContext = context;
    
    [self hc_injectJSExportImpl];
    
    self.appJSContext.exceptionHandler = ^(JSContext *context, JSValue *exception) {
        NSLog(@"HCJSCoreBase - JavaScript Exception : %@", exception);
        context.exception = exception;
    };
    
}

#pragma mark - HCJSCoreBaseProtocol
- (UIWebView *)getWebView {
    return nil;
}

- (void)injectJSExportImpl:(JSContext *)jsContext {};

@end
