//
//  HCNavigationJSExport.h
//  Demo
//
//  Created by 刘海川 on 2019/1/9.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>

NS_ASSUME_NONNULL_BEGIN

@protocol HCNavigationJSExport <JSExport>
JSExportAs(push,
           - (void)push:(NSString *)name type:(NSString *)type param:(NSDictionary *)param
           );
JSExportAs(goBack,
           - (void)goBack:(NSDictionary *)param
           );
- (void)goBackRoot;
JSExportAs(setBarTitle,
           - (void)setBarTitle:(NSString *)title
           );
JSExportAs(setBarRightButton,
           - (void)setBarRightButton:(NSDictionary *)itemInfo callback:(JSValue *)callback
           );
JSExportAs(setBarDoubleRightButton,
           - (void)setBarDoubleRightButton:(NSDictionary *)itemInfo1
                                  callback:(JSValue *)callback1
                                 itemInfo2:(NSDictionary *)itemInfo2
                                 callback2:(JSValue *)callback2
           );
JSExportAs(setBarLeftButton,
           - (void)setBarLeftButton:(NSDictionary *)itemInfo callback:(JSValue *)callback
           );
JSExportAs(setBarDoubleLeftButton,
           - (void)setBarDoubleLeftButton:(NSDictionary *)itemInfo1
                                 callback:(JSValue *)callback1
                                itemInfo2:(NSDictionary *)itemInfo2
                                callback2:(JSValue *)callback2
           );
JSExportAs(getRouteContext,
           - (void)getRouteContext:(JSValue *)callback
           );

@end

NS_ASSUME_NONNULL_END
