//
//  BotsJSCoreBaseViewController.h
//  BotsItoa
//
//  Created by 刘海川 on 2018/12/21.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <JavaScriptCore/JavaScriptCore.h>

@protocol HCJSCoreBaseProtocol <NSObject>
@optional
- (void)injectJSExportImpl:(JSContext *)jsContext;
@required
- (UIWebView *)getWebView;

@end

NS_ASSUME_NONNULL_BEGIN

@interface HCJSCoreBaseViewController : UIViewController <HCJSCoreBaseProtocol>

@property (nonatomic, strong, readonly) JSContext *appJSContext;

@property (nonatomic, strong) NSThread *jsThread;

- (void)executeJSValueThreadSafe:(JSValue *)value args:(NSArray *)args;

- (JSValue *)callJsBridge:(NSString *)methodName args:(NSArray *)args;

@end

NS_ASSUME_NONNULL_END
