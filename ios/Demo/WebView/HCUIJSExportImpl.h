//
//  HCUIJSExportImpl.h
//  BotsItoa
//
//  Created by 刘海川 on 2018/12/20.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "HCUIJSExport.h"
#import "HCJSCoreBaseViewController.h"
NS_ASSUME_NONNULL_BEGIN

@interface HCUIJSExportImpl : NSObject <HCUIJSExport>

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext;

@end

NS_ASSUME_NONNULL_END
