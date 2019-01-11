//
//  HCNavigationViewController.h
//  Demo
//
//  Created by 刘海川 on 2019/1/9.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCJSCoreBaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
@protocol HCNavigationDelegate <NSObject>

- (void)backInfo:(NSDictionary *)info;

@end
@interface HCNavigationJsViewController : HCJSCoreBaseViewController <HCNavigationDelegate, UIWebViewDelegate>

@property (nonatomic, copy) NSString *routerPath;
@property (nonatomic, copy) NSString *routerType;
@property (nonatomic, copy) NSDictionary *routerContext;
@property (nonatomic, weak) id <HCNavigationDelegate> navigationDelegate;

@end

NS_ASSUME_NONNULL_END
