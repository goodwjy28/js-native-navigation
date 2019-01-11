//
//  NSObject+BotsJSContext.m
//  JsCoreDemo
//
//  Created by 刘海川 on 2018/5/15.
//  Copyright © 2018年 刘海川. All rights reserved.
//

#import "NSObject+HCJSContext.h"
#import <JavaScriptCore/JavaScriptCore.h>
NSString * const HCDidCreateJSContextNotification = @"com.haichuan.liu.Demo.DidCreateJSContext";
@implementation NSObject (HCJSContext)

- (void)webView:(id)unuse didCreateJavaScriptContext:(JSContext *)ctx forFrame:(id)frame {
    [[NSNotificationCenter defaultCenter] postNotificationName:HCDidCreateJSContextNotification object:ctx];
}

@end
