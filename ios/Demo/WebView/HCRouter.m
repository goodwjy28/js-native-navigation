//
//  HCRouter.m
//  Demo
//
//  Created by 刘海川 on 2019/1/10.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCRouter.h"
#import "HCDetailViewController.h"
@interface HCRouter ()

@property (nonatomic, copy) NSDictionary *routerMapper;

@end

@implementation HCRouter

- (instancetype)init
{
    self = [super init];
    if (self) {
        _routerMapper = @{@"detail": @"HCDetailViewController"};
    }
    return self;
}

+ (instancetype)instance {
    static HCRouter *router;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        router = [HCRouter new];
    });
    return router;
}

- (UIViewController *)push:(NSString *)pathName {
    NSString *className = [_routerMapper objectForKey:pathName];
    Class _Class = NSClassFromString(className);
    UIViewController *viewController = [[_Class alloc] initWithNibName:className bundle:nil];
    if (!viewController) {
        viewController = [[_Class alloc] init];
    }
    return viewController;
}

@end
