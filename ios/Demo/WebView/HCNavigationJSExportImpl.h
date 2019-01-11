//
//  HCNavigationJSExportImpl.h
//  Demo
//
//  Created by 刘海川 on 2019/1/9.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HCNavigationJSExport.h"
#import "HCNavigationJsViewController.h"
NS_ASSUME_NONNULL_BEGIN

@interface HCNavigationJSExportImpl : NSObject <HCNavigationJSExport>

+ (instancetype)instance:(HCNavigationJsViewController *)vcContext;

@end

NS_ASSUME_NONNULL_END
