//
//  BotsStoreJSExportImpl.m
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCStoreJSExportImpl.h"

@interface HCStoreJSExportImpl ()

@property (nonatomic, weak) HCJSCoreBaseViewController *vcContext;

@end

@implementation HCStoreJSExportImpl

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext {
    HCStoreJSExportImpl *impl = [HCStoreJSExportImpl new];
    impl.vcContext = vcContext;
    return impl;
}

- (void)userDefaultSave:(NSString *)key value:(NSDictionary *)value result:(JSValue *)result {
    self.vcContext.jsThread = [NSThread currentThread];
    if (!key || [@"" isEqualToString:key] || !value) {
        [self.vcContext executeJSValueThreadSafe:result args:@[@NO]];
    } else {
        [[NSUserDefaults standardUserDefaults] setObject:value forKey:key];
        BOOL isSuccess = [[NSUserDefaults standardUserDefaults] synchronize];
        [self.vcContext executeJSValueThreadSafe:result args:@[@(isSuccess)]];
    }
}

- (void)userDefaultRead:(NSString *)key result:(JSValue *)result {
    self.vcContext.jsThread = [NSThread currentThread];
    if (!key || [@"" isEqualToString:key]) {
        [self.vcContext executeJSValueThreadSafe:result args:@[@NO]];
    } else {
        NSDictionary *object = [[NSUserDefaults standardUserDefaults] objectForKey:key];
        [self.vcContext executeJSValueThreadSafe:result args:@[@YES, object]];
    }
}

@end
