//
//  BotsUIJSExportImpl.m
//  BotsItoa
//
//  Created by 刘海川 on 2018/12/20.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import "HCUIJSExportImpl.h"
#import <JavaScriptCore/JavaScriptCore.h>
@interface HCUIJSExportImpl ()

@property (nonatomic, weak) HCJSCoreBaseViewController *vcContext;

@end

@implementation HCUIJSExportImpl

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext {
    HCUIJSExportImpl *impl = [HCUIJSExportImpl new];
    impl.vcContext = vcContext;
    return impl;
}

- (void)alert:(NSString *)title
      message:(NSString *)msg
       affirm:(JSValue *)affirm
       cancel:(JSValue *)cancel {
    self.vcContext.jsThread = [NSThread currentThread];
    dispatch_async(dispatch_get_main_queue(), ^{
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:title
                                                                                 message:msg
                                                                          preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *affirmAction = [UIAlertAction actionWithTitle:@"确认"
                                                               style:UIAlertActionStyleDefault
                                                             handler:^(UIAlertAction * _Nonnull action) {
                                                                 [self.vcContext executeJSValueThreadSafe:affirm args:@[]];
        }];
        UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消"
                                                               style:UIAlertActionStyleDefault
                                                             handler:^(UIAlertAction * _Nonnull action) {
                                                                 [self.vcContext executeJSValueThreadSafe:cancel args:@[]];
        }];
        [alertController addAction:affirmAction];
        [alertController addAction:cancelAction];
        [self.vcContext presentViewController:alertController animated:YES completion:nil];
    });
}

@end
