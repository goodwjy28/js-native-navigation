//
//  HCNavigationJSExportImpl.m
//  Demo
//
//  Created by 刘海川 on 2019/1/9.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCNavigationJSExportImpl.h"
#import "HCNavigationJsViewController.h"
#import "HCRouter.h"
#import "UIView+Navbar.h"

@interface HCNavigationJSExportImpl ()

@property (nonatomic, weak) HCNavigationJsViewController *vcContext;

@property (nonatomic, strong) JSValue *rightButtonValue;
@property (nonatomic, strong) JSValue *rightButtonValue2nd;
@property (nonatomic, strong) JSValue *leftButtonValue;
@property (nonatomic, strong) JSValue *leftButtonValue2nd;

@end
@implementation HCNavigationJSExportImpl

+ (instancetype)instance:(HCNavigationJsViewController *)vcContext {
    HCNavigationJSExportImpl *impl = [HCNavigationJSExportImpl new];
    impl.vcContext = vcContext;
    return impl;
}

- (void)getRouteContext:(JSValue *)callback {
    self.vcContext.jsThread = [NSThread currentThread];
    NSDictionary *routeContext = self.vcContext.routerContext ? : @{};
    [self.vcContext executeJSValueThreadSafe:callback args:@[routeContext]];
}

 - (void)push:(NSString *)name type:(NSString *)type param:(NSDictionary *)param {
    self.vcContext.jsThread = [NSThread currentThread];
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([@"native" isEqualToString:type]) {
            UIViewController *viewController = [[HCRouter instance] push:name];
            [self.vcContext.navigationController pushViewController:viewController animated:YES];
        } else {
            HCNavigationJsViewController *navitionViewController = [[HCNavigationJsViewController alloc] init];
            navitionViewController.routerPath = name;
            navitionViewController.routerType = type;
            navitionViewController.routerContext = param;
            navitionViewController.navigationDelegate = self.vcContext;
            [self.vcContext.navigationController pushViewController:navitionViewController animated:YES];
        }
    });
}

- (void)goBack:(NSDictionary *)param {
    dispatch_async(dispatch_get_main_queue(), ^{
        if (self.vcContext.navigationDelegate && [self.vcContext.navigationDelegate respondsToSelector:@selector(backInfo:)]) {
            [self.vcContext.navigationDelegate backInfo:param];
        }
        [self.vcContext.navigationController popViewControllerAnimated:YES];
    });
}

- (void)goBackRoot {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.vcContext.navigationController popToRootViewControllerAnimated:YES];
    });
}

- (void)setBarTitle:(NSString *)title {
    dispatch_async(dispatch_get_main_queue(), ^{
        self.vcContext.title = title;
    });
}

- (void)setBarRightButton:(NSDictionary *)itemInfo callback:(JSValue *)callback {
    self.rightButtonValue = callback;
    dispatch_async(dispatch_get_main_queue(), ^{
        self.vcContext.navigationItem.rightBarButtonItem = nil;
        self.vcContext.navigationItem.rightBarButtonItems = @[];
        UIBarButtonItem *rightItem = [self hc_getButtonItem:itemInfo
                                                  actionSEL:@selector(rightAction)];
        self.vcContext.navigationItem.rightBarButtonItem = rightItem;
    });
}

- (void)setBarDoubleRightButton:(NSDictionary *)itemInfo1
                       callback:(JSValue *)callback1
                      itemInfo2:(NSDictionary *)itemInfo2
                      callback2:(JSValue *)callback2 {
    self.rightButtonValue = callback1;
    self.rightButtonValue2nd = callback2;
    dispatch_async(dispatch_get_main_queue(), ^{
        self.vcContext.navigationItem.rightBarButtonItem = nil;
        self.vcContext.navigationItem.rightBarButtonItems = @[];
        UIBarButtonItem *rightItem1 = [self hc_getButtonItem:itemInfo1
                                                   actionSEL:@selector(rightAction)];
        UIBarButtonItem *rightItem2 = [self hc_getButtonItem:itemInfo2
                                                   actionSEL:@selector(rightAction2nd)];
        self.vcContext.navigationItem.rightBarButtonItems = @[rightItem1, rightItem2];
    });
}

- (void)setBarLeftButton:(NSDictionary *)itemInfo callback:(JSValue *)callback {
    self.leftButtonValue = callback;
    dispatch_async(dispatch_get_main_queue(), ^{
        self.vcContext.navigationItem.leftBarButtonItem = nil;
        self.vcContext.navigationItem.leftBarButtonItems = @[];
        UIBarButtonItem *rightItem = [self hc_getButtonItem:itemInfo
                                                  actionSEL:@selector(leftAction)];
        self.vcContext.navigationItem.leftBarButtonItem = rightItem;
    });
}

- (void)setBarDoubleLeftButton:(NSDictionary *)itemInfo1
                       callback:(JSValue *)callback1
                      itemInfo2:(NSDictionary *)itemInfo2
                      callback2:(JSValue *)callback2 {
    self.leftButtonValue = callback1;
    self.leftButtonValue2nd = callback2;
    dispatch_async(dispatch_get_main_queue(), ^{
        self.vcContext.navigationItem.leftBarButtonItem = nil;
        self.vcContext.navigationItem.leftBarButtonItems = @[];
        UIBarButtonItem *rightItem1 = [self hc_getButtonItem:itemInfo1
                                                   actionSEL:@selector(leftAction)];
        UIBarButtonItem *rightItem2 = [self hc_getButtonItem:itemInfo2
                                                   actionSEL:@selector(leftAction2nd)];
        self.vcContext.navigationItem.leftBarButtonItems = @[rightItem1, rightItem2];
    });
}

- (void)rightAction {
    if (self.rightButtonValue) {
        [self.vcContext executeJSValueThreadSafe:self.rightButtonValue args:@[]];
    }
}

- (void)rightAction2nd {
    if (self.rightButtonValue2nd) {
        [self.vcContext executeJSValueThreadSafe:self.rightButtonValue2nd args:@[]];
    }
}

- (void)leftAction {
    if (self.leftButtonValue) {
        [self.vcContext executeJSValueThreadSafe:self.leftButtonValue args:@[]];
    }
}

- (void)leftAction2nd {
    if (self.leftButtonValue2nd) {
        [self.vcContext executeJSValueThreadSafe:self.leftButtonValue2nd args:@[]];
    }
}

- (UIBarButtonItem *)hc_getButtonItem:(NSDictionary *)itemInfo
                                 actionSEL:(SEL)action {
    NSString *itemImage = itemInfo[@"image"];
    NSString *itemTitle = itemInfo[@"title"];
    UIBarButtonItem *buttonItem = nil;
    if (![itemImage isEqualToString:@""]) {
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button addTarget: self action:action forControlEvents: UIControlEventTouchUpInside];
        button.imageView.contentMode = UIViewContentModeScaleAspectFit;
        buttonItem = [[UIBarButtonItem alloc] initWithCustomView:button];
        NSURL *imgUrl = [NSURL URLWithString:itemImage];
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            NSError *error;
            NSData *imgData = [NSData dataWithContentsOfURL:imgUrl options:NSDataReadingMappedIfSafe error:&error];
            NSLog(@"error:%@",error);
            UIImage *image = [UIImage imageWithData:imgData];
            dispatch_async(dispatch_get_main_queue(), ^{
                [button setImage:image forState:UIControlStateNormal];
            });
        });
        [button applyNavBarConstraints:33 height:33];
    } else if (![itemTitle isEqualToString:@""]) {
        buttonItem = [[UIBarButtonItem alloc] initWithTitle:itemTitle style:UIBarButtonItemStylePlain target:self action:action];
    }
    return buttonItem;
}

@end
